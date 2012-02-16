package com.redshape.ui.data.state;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.UnhandledUIException;
import com.redshape.ui.data.IStore;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.utils.Settings;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.serializing.ObjectsFlusher;
import com.redshape.utils.serializing.ObjectsLoader;
import com.redshape.utils.serializing.ObjectsLoaderException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * @author root
 * @date 07/04/11
 * @package com.redshape.ui.state
 */
public class UIStateManager extends EventDispatcher implements IUIStateManager {
    private static final Logger log = Logger.getLogger(UIStateManager.class);

	protected enum OperationType {
		RESTORE,
		BACKUP
	}

	private boolean initialized;

    private InputStream inputStream;
    private OutputStream outputStream;

    private ObjectsLoader loader;
    private ObjectsFlusher flusher;

    private int revision;

	private boolean doVersioning;

    private String location;
    private Collection<UIStateFeature> features = new HashSet<UIStateFeature>();

    private boolean periodicEnabled;
    private int periodicInterval;

    private Timer timer = new Timer();

    public UIStateManager( ObjectsLoader loader, ObjectsFlusher flusher ) {
        this.loader = loader;
        this.flusher = flusher;

		this.initListeners();
    }

	protected void initListeners() {
		Dispatcher.get().addListener(
			UIEvents.Core.Init,
			new IEventHandler() {
				private static final long serialVersionUID = 4677189791915621814L;

				@Override
				public void handle(AppEvent event) {
					UIStateManager.this.init();

					try {
						UIStateManager.this.restore();
					} catch ( StateException e ) {
						log.error("Initial restoration failed...", e );
					}
				}
			}
        );

		Dispatcher.get().addListener(
			UIStateEvent.Restore,
			new IEventHandler() {
				private static final long serialVersionUID = -7791174491040399468L;

				@Override
				public void handle(AppEvent event) {
					try {
						if ( event.getArgs().length != 0 ) {
							UIStateManager.this.restore( event.<Integer>getArg(0) );
						} else {
							UIStateManager.this.restore();
						}
					} catch ( Throwable e ) {
						throw new UnhandledUIException( e.getMessage(), e );
					}
				}
			}
		);

		Dispatcher.get().addListener(
			UIStateEvent.Save,
			new IEventHandler() {
				private static final long serialVersionUID = -2420080872688532957L;

				@Override
				public void handle(AppEvent event) {
					try {
						UIStateManager.this.backup();
					} catch ( Throwable e ) {
						throw new UnhandledUIException( e.getMessage(), e );
					}
				}
			}
		);
	}

    protected void init() {
		this.initialized = true;

		this.enableFeature( UIStateFeature.SettingsBackup );
        this.enableFeature( UIStateFeature.StoragesBackup );

		if ( this.isPeriodicEnabled() ) {
			this.startTimer();
		}
    }

    protected void stopTimer() {
        this.timer.cancel();
    }

    protected void startTimer() {
        this.timer.scheduleAtFixedRate(new PeriodicTask(this), this.getPeriodicInterval(), this.getPeriodicInterval());
    }

    protected void restartTimer() {
        this.timer.purge();
        this.startTimer();
    }

    protected String getLocation() {
        return this.location;
    }

	@Override
	public void setDoVersioning( boolean value ) {
		this.doVersioning = value;
	}

	@Override
	public boolean doVersioning() {
		return this.doVersioning;
	}

    public void setEnabledFeatures( Collection<UIStateFeature> features ) {
        this.features = features;
    }

    @Override
    public void setLocation( String location ) {
        this.location = location;
    }

    protected ObjectsLoader getLoader() {
        return loader;
    }

    protected ObjectsFlusher getFlusher() {
        return flusher;
    }

	protected synchronized int nextRevision() {
		return this.doVersioning() ? (this.revision = this.revision++)
					: this.revision;
	}

    @Override
    public synchronized void backup() throws StateException {
        try {
            this.getFlusher().flush(this.collectItems(), this.openOutputStream(this.nextRevision()));
			this.closeOutputStream();
			this.forwardEvent(UIStateEvent.Result.Save);
        } catch ( IOException e ) {
            throw new StateException( e.getMessage(), e );
        } catch ( ObjectsLoaderException e ) {
            throw new StateException( "Marshalling exception", e);
        }
    }

	@Override
	public void restore() throws StateException {
		this.restore( this.getRevision() );
	}

    @Override
    public synchronized void restore(Integer revision) throws StateException {
        try {
            this._applyRestored(
                this.getLoader().<Map<UIStateFeature, Object>>loadObject(
                    new HashMap<UIStateFeature, Object>(),
                    this.openInputStream(revision)
            )   );
			this.closeInputStream();
			this.forwardEvent( UIStateEvent.Result.Restore );
        } catch ( FileNotFoundException e ) {
        } catch ( IOException e ) {
            throw new StateException( e.getMessage(), e );
        } catch ( ObjectsLoaderException e  ) {
            throw new StateException( "Unmarshalling exception", e );
        }
    }

    protected void _applyRestored( Map<UIStateFeature, Object> data ) {
        for ( UIStateFeature feature : data.keySet() ) {
            switch ( feature ) {
            case SettingsBackup:
				final Settings settings = (Settings) data.get( UIStateFeature.SettingsBackup );
				if ( settings == null ) {
					continue;
				}

				UIRegistry.setSettings(settings);

				Dispatcher.get().forwardEvent( UIEvents.Core.Refresh.Settings );
            break;
            case StoragesBackup:
                @SuppressWarnings("unchecked")
				final Collection<IStore<?>> stores = (Collection<IStore<?>>) data.get(feature);
                if ( stores == null || stores.size() == 0 ) {
                    continue;
                }

				for ( IStore<?> store : stores ) {
					UIRegistry.getStoresManager().register(store);
				}

				Dispatcher.get().forwardEvent( UIEvents.Core.Refresh.Stores );
            break;
            }
        }
    }

    @Override
    public void enableFeature( UIStateFeature feature ) {
        this.features.add(feature);
    }

    @Override
    public void disableFeature( UIStateFeature feature ) {
        this.features.remove(feature);
    }

    @Override
    public boolean isFeatureEnabled( UIStateFeature feature ) {
        return this.features.contains( feature );
    }

    @Override
    public Integer getRevision() {
        return this.revision;
    }

    @Override
    public Map<Date, Integer> getRevisions() {
        return null;
    }

    @Override
    public void setPeriodicEnabled(boolean value) {
        if ( value && !this.periodicEnabled && this.initialized ) {
            this.startTimer();
        }

        this.periodicEnabled = value;

        if ( !this.isPeriodicEnabled() ) {
            this.stopTimer();
        }
    }

    @Override
    public boolean isPeriodicEnabled() {
        return this.periodicEnabled;
    }

    @Override
    public void setPeriodicInterval(int value) {
		boolean changed = value == this.periodicInterval;
        this.periodicInterval = value;

		if ( changed ) {
			this.restartTimer();
		}
    }

    @Override
    public int getPeriodicInterval() {
        return this.periodicInterval;
    }

    protected Map<UIStateFeature, Object> collectItems() {
        Map<UIStateFeature, Object> result = new HashMap<UIStateFeature, Object>();

        if ( this.isFeatureEnabled( UIStateFeature.StoragesBackup ) ) {
            result.put( UIStateFeature.StoragesBackup, UIRegistry.getStoresManager().list() );
        }

        if ( this.isFeatureEnabled( UIStateFeature.SettingsBackup ) ) {
            log.warn("Settings backup not implemented yet...");
            result.put( UIStateFeature.SettingsBackup, UIRegistry.getSettings() );
        }

        return result;
    }

    protected File openFile( int revision, OperationType type ) throws IOException {
		if ( this.getLocation() == null ) {
            throw new IOException("File location not specified");
        }

        File file = new File( this.getLocation() + "/" + revision + ".xml" );
		if ( !file.exists() ) {
			if ( type.equals( OperationType.RESTORE ) ) {
				throw new FileNotFoundException();
			} else {
				file.createNewFile();
			}
		}

		return file;
    }

	protected void closeOutputStream() throws IOException {
		this.outputStream.close();
		this.outputStream = null;
	}

    protected OutputStream openOutputStream( int revision ) throws IOException {
        return this.outputStream != null ? this.outputStream
                : ( this.outputStream = new FileOutputStream( this.openFile(revision, OperationType.BACKUP) ) );
    }

	protected void closeInputStream() throws IOException {
		this.inputStream.close();
		this.inputStream = null;
	}

    protected InputStream openInputStream( int revision ) throws IOException {
        return this.inputStream != null ? this.inputStream
                : ( this.inputStream = new FileInputStream( this.openFile(revision, OperationType.RESTORE) ) );
    }

    public class PeriodicTask extends TimerTask {
        private IUIStateManager periodicContext;

        public PeriodicTask( IUIStateManager periodicContext ) {
            this.periodicContext = periodicContext;
        }

        @Override
        public void run() {
            try {
                this.periodicContext.backup();
            } catch ( StateException e ) {
                throw new UnhandledUIException( e.getMessage(), e );
            }
        }
    }
}

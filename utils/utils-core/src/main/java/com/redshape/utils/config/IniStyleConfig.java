package com.redshape.utils.config;

import com.redshape.utils.config.sources.IConfigSource;

/**
 * @package com.redshape.utils.config
 * @user cyril
 * @date 6/22/11 4:43 PM
 */
public class IniStyleConfig extends AbstractConfig {
    private static final long serialVersionUID = 3682806047106538553L;

    protected IniStyleConfig(IConfig parent, String name, String value) {
		super(parent, name, value);
    }

    protected IniStyleConfig(String name, String value) {
        this(null, name, value);
    }

    public IniStyleConfig(IConfigSource source) throws ConfigException {
		super(source);
    }

    @Override
    protected void actualInit() throws ConfigException {
        String data = this.source.read();
        if ( data.isEmpty() ) {
            return;
        }

        this.processData( data );
    }

	@Override
	protected IConfig createNull() {
		IniStyleConfig config = new IniStyleConfig(null, null, null);
		config.nulled = true;
		return config;
	}

	protected void processData( String data ) throws ConfigException {
        String[] lines = data.split("[\\n]+");
        IConfig context = null;
        for ( String line : lines ) {
            if ( line.startsWith("[") ) {
                if ( !line.endsWith("]") ) {
                    throw new ConfigException("Syntax exception");
                }

                String[] parts = line.replace("[", "").replace("]", "").split("\\s");
                context = this.createChild(parts[0]);
                if ( parts.length > 1 && !parts[1].isEmpty() ) {
                    context.set( parts[1] );
                }
            } else if ( line.contains("=") ) {
                if ( context == null ) {
                    throw new ConfigException("Unbinded property definition!");
                }

                String[] parts = line.split("=");
                context.attribute( parts[0].trim(), parts[1].trim() );
            }
        }
    }

    @Override
    public IConfig createChild(String name) {
        if ( name == null ) {
            throw new IllegalArgumentException("<null>");
        }

        IniStyleConfig config = new IniStyleConfig(this, name, "");
        this.append(config);
        return config;
    }

    @Override
    public String serialize() throws ConfigException {
        StringBuffer result = new StringBuffer();
        for ( IConfig config : this.childs() ) {
            result.append("[")
                  .append( config.name() )
                  .append(" ");
            if ( config.value() != null && !config.value().isEmpty() ) {
                result.append( config.value() );
            }

            result.append("]")
                  .append("\n");

            for ( String key : config.attributeNames() ) {
                result.append( key )
                      .append( "=" )
                      .append( config.attribute(key) )
                      .append("\n");
            }
        }

        return result.toString();
    }

}

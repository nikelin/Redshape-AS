package com.redshape.utils.config;

import com.redshape.utils.StringUtils;

import java.io.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.config
 * @date 10/20/11 1:05 PM
 */
public class PropertiesConfig extends AbstractConfig {
	public static final String[] STANDARD_LINE_DELIMITERS = new String[] { "\n", "\r", ";" };

	private IConfig parent;
	private String name;
	private String value;
	private File file;

	protected PropertiesConfig(PropertiesConfig parent, String name, String value) {
        super(parent, name, value);
    }

    protected PropertiesConfig(String name, String value) {
        this(null, name, value);
    }

    public PropertiesConfig(File file) throws ConfigException {
		super(file);
    }

    protected void init() throws ConfigException {
        try {
            String data = this.readFile();
            if ( data.isEmpty() ) {
                return;
            }

            this.processData( data );
        } catch ( IOException e ) {
            throw new ConfigException( e.getMessage(), e );
        }
    }

	protected String readFile() throws IOException {
        StringBuilder result = new StringBuilder();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader( new InputStreamReader( new FileInputStream( file ) ) );
            String tmp;
            while ( null != ( tmp = reader.readLine() ) ) {
                result.append( tmp ).append("\n");
            }
        } finally {
            if ( reader != null ) {
                reader.close();
            }
        }

        return result.toString();
    }

	@Override
	protected IConfig createNull() {
		PropertiesConfig config = new PropertiesConfig(this, null, null);
		config.nulled = true;
		return config;
	}

	protected String[] getLineDelimiters() {
		return STANDARD_LINE_DELIMITERS;
	}

	protected void processData( String data ) throws ConfigException {
		String[] lines = data.split( StringUtils.join(this.getLineDelimiters(), "") );
		for ( String line : lines ) {
			String[] parts = line.split("=");
			if ( parts.length != 2 ) {
				throw new ConfigException("Config file is corrupted");
			}

			this.append( new PropertiesConfig(this, parts[0], parts[1] ) );
		}
	}

	@Override
	public String serialize() throws ConfigException {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public IConfig createChild(String name) {
		return new PropertiesConfig(this, name, null);
	}
}

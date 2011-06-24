package com.redshape.utils.config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @package com.redshape.utils.config
 * @user cyril
 * @date 6/22/11 4:43 PM
 */
public class PropertiesConfig implements IWritableConfig {
    private static final long serialVersionUID = 3682806047106538553L;
    private File file;

    private boolean nulled;
    private String name;
    private String value;
    private IConfig parent;
    private List<IConfig> childs = new ArrayList<IConfig>();
    private Map<String, String> attributes = new HashMap<String, String>();

    protected PropertiesConfig( String name, String value ) {
        this.name = name;
        this.value = value;
    }

    public PropertiesConfig( File file ) throws ConfigException {
        this.file = file;

        this.init();
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

    protected void processData( String data ) throws ConfigException {
        String[] lines = data.split("[\\n]+");
        IWritableConfig context = null;
        for ( String line : lines ) {
            if ( line.startsWith("[") ) {
                if ( !line.endsWith("]") ) {
                    throw new ConfigException("Syntax exception");
                }

                line = line.replace("[", "").replace("]", "");

                String[] parts = line.split("\\s");
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
    public String[] attributeNames() {
        return this.attributes.keySet().toArray( new String[this.attributes.size()] );
    }

    @Override
    public boolean isNull() {
        return this.nulled;
    }

    @Override
    public IConfig get(String name) throws ConfigException {
        for ( IConfig config : this.childs() ) {
            if ( config.name() != null && config.name().equals(name ) ) {
                return config;
            }
        }

        return null;
    }

    @Override
    public <T extends IConfig> T[] childs() {
        return (T[]) this.childs.toArray( new IConfig[this.childs.size()] );
    }

    @Override
    public boolean hasChilds() {
        return this.childs.isEmpty();
    }

    @Override
    public String[] list() {
        String[] names = new String[this.childs().length];

        int i = 0;
        for ( IConfig child : this.childs() ) {
            names[i++] = child.name();
        }

        return names;
    }

    @Override
    public String[] list(String name) {
        List<String> values = new ArrayList<String>();
        for ( IConfig child : this.childs() ) {
            values.add( child.value() );
        }

        return values.toArray( new String[ values.size() ] );
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String[] names() {
        String[] names = new String[ this.childs().length ];
        int i = 0;
        for ( IConfig child : this.childs() ) {
            names[i++] = child.name();
        }

        return names;
    }

    @Override
    public String attribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public IWritableConfig append(IConfig config) {
        if ( config == null || config.name() == null ) {
            throw new IllegalArgumentException("<null>");
        }

        this.childs.add( config );
        return this;
    }

    @Override
    public IWritableConfig makeWritable(boolean value) {
        return this;
    }

    @Override
    public IWritableConfig set(String value) throws ConfigException {
        this.value = value;
        return this;
    }

    @Override
    public IWritableConfig attribute(String name, String value) {
        this.attributes.put(name, value);
        return this;
    }

    @Override
    public IWritableConfig createChild(String name) {
        if ( name == null ) {
            throw new IllegalArgumentException("<null>");
        }

        PropertiesConfig config = new PropertiesConfig(name, "");
        config.parent = this;
        this.append(config);
        return config;
    }

    public void save() throws ConfigException {
        if ( this.file == null ) {
            throw new IllegalStateException("Associated holder not exists");
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter( new FileOutputStream(this.file) ) );
            writer.write( this.serialize() );
            writer.close();
        } catch ( IOException e ) {
            throw new ConfigException("File update failed", e );
        }
    }

    @Override
    public IWritableConfig remove() throws ConfigException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public IConfig parent() throws ConfigException {
        return this.parent;
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

    @Override
    public <V> V getRawElement() {
        throw new UnsupportedOperationException();
    }

}

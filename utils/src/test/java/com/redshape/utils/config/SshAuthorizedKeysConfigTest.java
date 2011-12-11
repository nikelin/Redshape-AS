package com.redshape.utils.config;

import com.redshape.utils.tests.AbstractContextAwareTest;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SshAuthorizedKeysConfigTest extends AbstractContextAwareTest {
    private File file;
    
    public SshAuthorizedKeysConfigTest() {
        super("src/test/resources/context.xml");
        
        this.file = new File( this.getContext().getBean("sshConfigPath", String.class) );
    }

    @Test
    public void testMain() throws ConfigException {
        SshAuthorizedKeysConfig config = new SshAuthorizedKeysConfig( this.file );
        for ( IConfig commandNode : config.childs() ) {
            assertFalse( commandNode.get("user").isNull() );
            assertFalse( commandNode.get("ssh-rsa").isNull() );
        }
        
        assertEquals( "self@nikelin-ru", config.childs().get(0).attribute("user") );
        assertEquals( "git@cyril-Desktop", config.childs().get(1).attribute("user") );
        assertEquals( "AAAAB3NzaC1yc2EAAAADAQABAAABAQC+5ufQrTC9u1nY6sRS39UquOxl7cnpWQKus1MfIMAUt" +
                "FyiW6DlFLZ/WVgZylvVe6WTvKQoO4PecEt21MPNY0/C1uMncN1gfs288+u7LSRzK4ZRIujmMG8QntFj8LBudS2UX" +
                "4nI7qlIVP9zNwOb8x9ZT4oA4uFu/Kf7je/4yZH7ru3tHQlwvHav/Q6SosVgIE3xFH9E85A4uEBsLJZusJ1CPpobre1o" +
                "XrNgjbj6Uar18hSME3Z1VMsPLEHdyrP4HzK4KVQQLKNjK7udr5sgdjbGvSPMajsV2EYIc6Zr/3GuX/ULOvjR9ZD73u4o2h" +
                "kxMyp8P+OC8hBZxiL1VLH15lZR", config.childs().get(0).attribute("ssh-rsa") );
        assertEquals( "/opt/projectshost/serve 25", config.childs().get(0).value() );
        assertEquals( "/opt/projectshost/serve 36", config.childs().get(1).value() );

        assertEquals( 2, config.childs().size() );
    }

}

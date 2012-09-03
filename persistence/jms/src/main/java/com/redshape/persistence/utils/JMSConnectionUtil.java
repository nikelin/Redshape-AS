package com.redshape.persistence.utils;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.utils
 * @date 1/25/12 {4:35 PM}
 */
public class JMSConnectionUtil {

    public static final QueueConnection createConnection()
            throws JMSException, NamingException {
        InitialContext ctx = new InitialContext();
        QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("queue/connectionFactory");
        QueueConnection connection = factory.createQueueConnection();

        connection.start();

        return connection;
    }

}

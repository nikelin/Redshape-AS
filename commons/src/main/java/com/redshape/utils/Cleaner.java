package com.redshape.utils;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: flare
 * Date: 14.07.2010
 * Time: 12:53:42
 * To change this template use File | Settings | File Templates.
 */
public class Cleaner implements Runnable {

    private static final Logger log = Logger.getLogger( Cleaner.class );

    //TODO: incriment params what we want to clean
    public Cleaner()
    {}

    //TODO: дописать сюда механизм удаления сушьности чата, если модель закрыла чат,сессии по тайму
    // и накрутить lazy средствами hibernate, сделав этот таск демоном
    @Override
    public void run() {
        /*try {

            List<Session> oldSessions =  (List<Session>)ManagersFactory.getDefault().getForEntity(Session.class).findAll();
            int oldCount = oldSessions.size();
            for(int i=0;i< oldCount;i++) {
                 Session s = oldSessions.get(i);
                 if(s != null && s.isExists()) {
                       s.remove();
                 }
            }
            oldSessions.clear();
        } catch (ManagerException e) {
            log.info("cannot clean sessions please check loading or database");
        }
        try {
            List<Chat> oldChats =  (List<Chat>)ManagersFactory.getDefault().getForEntity(Chat.class).findAll();
            int oldCount = oldChats.size();
            for(int i=0;i< oldCount;i++) {
                Chat c = oldChats.get(i);
                 if(c != null && c.isExists())
                       c.remove();
            }
            
            oldChats.clear();
        } catch (ManagerException e) {
            log.info("cannot clean Chats please check loading or database");
        }

        ISocketServer server = SocketServerFactory.getInstance().getInstance(ApplicationServer.class);

        AuthenticatorInterface authenticator = AuthenticatorFactory.getInstance().getAdapter( IRequester.class );
        if(server != null)
        {
           Map<Object, IIdentity> authenticated = authenticator.getAuthenticated();

            for ( Object key : authenticated.keySet() ) {
                IIdentity identity = authenticated.get(key);
                authenticator.unauthorize( identity );
             }
         }
          */
    }
}

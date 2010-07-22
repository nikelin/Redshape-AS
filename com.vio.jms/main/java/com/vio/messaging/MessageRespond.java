package com.vio.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date May 5, 2010
 */
public class MessageRespond implements IMessageRespond {
    public Map<String, Object> params = new HashMap<String, Object>();
    private Boolean success;

    public MessageRespond() {}

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void markSuccess( Boolean status ) {
        this.success = status;
    }

    public Boolean isSuccessful() {
        return this.success;
    }
}

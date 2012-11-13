/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.daemon;

import java.util.Date;

/**
 * @author nikelin
 */
public interface ILogEvent extends IDaemonEvent {

    public Date getDateTime();

    public String getWork();

    public void setWork(String work);
    
    public String getMessage();

    public String getSeverety();

    public void setDateTime(final Date dateTime);

    public void setMessage(final String message);

    public void setSeverety(final String severety);

    public void setMessageClass(String messageClass);

    public String getMessageClass();
}

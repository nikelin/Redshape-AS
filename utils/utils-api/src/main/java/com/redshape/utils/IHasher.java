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

package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: flare
 * Date: 21.06.2010
 * Time: 12:55:51
 * To change this template use File | Settings | File Templates.
 */
public interface IHasher {
    
    public boolean checkEquality( String origin, String hashed );

    public String hashBase64( Object text );

    public String hash( Object text );

    public String getName();
   
}
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

package com.redshape.plugins.packagers;

import com.redshape.utils.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.loaders
 * @date 10/11/11 12:28 PM
 */
public class PackagingType implements IEnum<String> {
	private String name;
	private String[] extensions;

	protected PackagingType( String name, String[] extensions ) {
		this.name = name;
		this.extensions = extensions;
        
        REGISTRY.put( name, this );
	}

    public static final Map<String, PackagingType> REGISTRY = new HashMap<String, PackagingType>();
    
	/**
	 * Must be used in a cases when package type detection makes impossible
	 */
	public static final PackagingType UNKNOWN = new PackagingType("Package.Unknown", new String[] {} );

	/**
	 * Represents inline package which is not compressed
	 * by any of archive utils
	 */
	public static final PackagingType INLINE = new PackagingType("Package.Inline", new String[] {} );
	/**
	 * Represents plugin packaged by ZIP and inherits Java Archive
	 * packaging layout
	 */
	public static final PackagingType JAR = new PackagingType("Package.JAR", new String[] { "jar" });
	/**
	 * Represents plugin packaged by ZIP and inherits Python Archive
	 * packaging layout
	 */
	public static final PackagingType PAR = new PackagingType("Package.PAR", new String[] { "par", "pyr" });
	/**
	 * Represents plugin packaged by ZIP and inherits Java Web Archive packaging
	 * layout
	 */
	public static final PackagingType WAR = new PackagingType("Package.WAR", new String[] { "war" } );
	/**
	 * Represents plugin packaged by ZIP and inherits Java Enterprise Archive packaging
	 * layout
	 */
	public static final PackagingType EAR = new PackagingType("Package.EAR", new String[] { "ear" } );
	/**
	 * Represents plugin packaged by ZIP and inherits PHP Archive packaing
	 * layout
	 */
	public static final PackagingType PHAR = new PackagingType("Package.PHAR", new String[] { "phar" } );

    public static PackagingType valueOf( String path ) {
        PackagingType type = REGISTRY.get(path);
        if ( null != type ) {
            return type;
        }

        for ( PackagingType packagingType : REGISTRY.values() ) {
            if ( -1 != Arrays.binarySearch(packagingType.extensions(), path) ) {
                return packagingType;
            }
        }

        return null;
    }
    
    public String[] extensions() {
        return this.extensions;
    }
    
	@Override
	public String name() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof PackagingType
				&& (( PackagingType ) obj).name().equals( this.name()  );
	}
}

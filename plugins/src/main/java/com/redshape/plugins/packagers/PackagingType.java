package com.redshape.plugins.packagers;

import com.redshape.utils.IEnum;

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
	}

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

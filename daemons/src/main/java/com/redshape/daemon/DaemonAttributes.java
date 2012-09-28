package com.redshape.daemon;

/**
 * @author nikelin
 */
public class DaemonAttributes implements IDaemonAttributes {
    private String code;

	protected DaemonAttributes(String code) {
		this.code = code;
	}

	public static final DaemonAttributes ID = new DaemonAttributes("ID");

	public static final DaemonAttributes SERVICE_NAME = new DaemonAttributes("SERVICE_NAME");

	public static final DaemonAttributes MAX_ATTEMPTS = new DaemonAttributes("MAX_ATTEMPTS");

    public static final DaemonAttributes PORT = new DaemonAttributes("PORT");

    public static final DaemonAttributes HOST = new DaemonAttributes("HOST");

    public static final DaemonAttributes NAME = new DaemonAttributes("NAME");

    public static final DaemonAttributes REGISTRATION_URL = new DaemonAttributes("REGISTRATION_URL");

	public static final DaemonAttributes MAX_CONNECTIONS = new DaemonAttributes("MAX_CONNECTIONS");

    public static final DaemonAttributes MAX_REG_ATTEMPTS = new DaemonAttributes("MAX_REG_ATTEMPTS");

    public static final DaemonAttributes MAX_REG_TIMEOUT = new DaemonAttributes("MAX_REG_TIMEOUT");

    public static final DaemonAttributes WORKING_STRATEGY_NAME = new DaemonAttributes("WORKING_STRATEGY_NAME");

    public static final DaemonAttributes LOCATION = new DaemonAttributes("LOCATION");

    public static final DaemonAttributes LAST_PING_TIME = new DaemonAttributes("LAST_PING_TIME");

    public static final DaemonAttributes DO_PUBLISH = new DaemonAttributes("DO_PUBLISH");

    public static final DaemonAttributes DO_REGISTERING = new DaemonAttributes("DO_REGISTERING");

	@Override
	public String name() {
		return this.code;
	}

	@Override
	public int hashCode() {
		return this.code.hashCode();
	}

}

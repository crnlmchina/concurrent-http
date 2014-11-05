/**
 * 
 */
package org.ring.concurrenthttp.client;

import java.io.Serializable;

/**
 * Connection configs of request
 * 
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class ConnectionProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Retry times with timeout, 0 as no retry
	 */
	private int retries;

	/**
	 * Max connections, 0 as no limit
	 */
	private int maxConnections;

	/**
	 * default timeout used for request without timeout set
	 */
	private int defaultTimeout;

	/**
	 * If use keepAlived connection
	 */
	private boolean keepAlive;

	public ConnectionProfile(int retries, int maxConnections, int defaultTimeout) {
		super();
		this.retries = retries;
		this.maxConnections = maxConnections;
		this.defaultTimeout = defaultTimeout;
	}

	public int getRetries() {
		return retries;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public int getDefaultTimeout() {
		return defaultTimeout;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

}

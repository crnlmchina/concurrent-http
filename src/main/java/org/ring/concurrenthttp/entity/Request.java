/**
 * 
 */
package org.ring.concurrenthttp.entity;

import java.io.Serializable;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;

import com.google.common.base.Preconditions;

/**
 * Request
 * 
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id to mark the request
	 */
	private final String requestId;
	private final HttpRequestBase request;
	private final HttpContext context;
	private int timeout;

	public Request(String requestId, HttpRequestBase request, HttpContext context) {
		super();
		this.requestId = Preconditions.checkNotNull(requestId);
		this.request = Preconditions.checkNotNull(request);
		this.context = context;
	}

	public String getRequestId() {
		return requestId;
	}

	public HttpRequestBase getRequest() {
		return request;
	}

	public HttpContext getContext() {
		return context;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}

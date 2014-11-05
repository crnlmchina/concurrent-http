/**
 * 
 */
package org.ring.concurrenthttp.entity;

import java.io.Serializable;

import org.apache.http.HttpResponse;

/**
 * Response
 * 
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id to mark the request of the response
	 */
	private final String requestId;

	private final HttpResponse httpResponse;

	public Response(String requestId, HttpResponse httpResponse) {
		super();
		this.requestId = requestId;
		this.httpResponse = httpResponse;
	}

	public String getRequestId() {
		return requestId;
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

}

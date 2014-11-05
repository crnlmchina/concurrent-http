/**
 * 
 */
package org.ring.concurrenthttp.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class MultiRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Request> requests;

	public List<Request> getRequests() {
		return requests;
	}

	private MultiRequest() {
		super();
		requests = Lists.newArrayList();
	}

	public static final MultiRequest create() {
		return new MultiRequest();
	}

	public MultiRequest append(Request request) {
		requests.add(request);
		return this;
	}
}

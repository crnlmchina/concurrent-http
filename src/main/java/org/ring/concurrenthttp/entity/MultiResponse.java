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
public class MultiResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Response> responses;

	public List<Response> getResponses() {
		return responses;
	}

	private MultiResponse() {
		super();
		responses = Lists.newArrayList();
	}

	public static final MultiResponse create() {
		return new MultiResponse();
	}

	public MultiResponse append(Response response) {
		responses.add(response);
		return this;
	}
}

/**
 * 
 */
package org.ring.concurrenthttp.client;

import java.io.Closeable;

import org.ring.concurrenthttp.entity.MultiRequest;
import org.ring.concurrenthttp.entity.MultiResponse;

/**
 * Concurrent Multiple requests
 * 
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public interface HttpPipline extends Closeable {

	MultiResponse query(MultiRequest multiRequest);

}

/**
 * 
 */
package org.ring.concurrenthttp.test;

import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.ring.concurrenthttp.client.ConnectionProfile;
import org.ring.concurrenthttp.client.HttpPipline;
import org.ring.concurrenthttp.client.SimpleHttpPipline;
import org.ring.concurrenthttp.entity.MultiRequest;
import org.ring.concurrenthttp.entity.MultiResponse;
import org.ring.concurrenthttp.entity.Request;
import org.ring.concurrenthttp.entity.Response;

/**
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class HttpPiplineTest {

	@Test
	public void testShortConnection() throws ParseException, IOException {

		HttpPipline httpPipline = null;
		try {
			ConnectionProfile connectionProfile = new ConnectionProfile(2, 100, 1000);
			httpPipline = new SimpleHttpPipline(connectionProfile);

			MultiRequest multiRequest = MultiRequest.create();
			HttpRequestBase request1 = new HttpGet("http://www.baidu.com/");
			multiRequest.append(new Request("Baidu", request1, null));
			HttpRequestBase request2 = new HttpGet("http://cn.bing.com/");
			multiRequest.append(new Request("Bing", request2, null));

			MultiResponse multiResponse = httpPipline.query(multiRequest);
			List<Response> responses = multiResponse.getResponses();

			for (Response response : responses) {
				System.out.println(response.getRequestId());
				String body = EntityUtils.toString(response.getHttpResponse().getEntity(), Charsets.UTF_8);
				System.out.println(body);
			}
		} finally {
			if (httpPipline != null)
				httpPipline.close();
		}

	}
	
	@Test
	public void testLongConnection() throws ParseException, IOException {

		HttpPipline httpPipline = null;
		try {
			ConnectionProfile connectionProfile = new ConnectionProfile(2, 100, 1000);
			connectionProfile.setKeepAlive(true);
			httpPipline = new SimpleHttpPipline(connectionProfile);

			MultiRequest multiRequest = MultiRequest.create();
			HttpRequestBase request1 = new HttpGet("http://www.baidu.com/");
			multiRequest.append(new Request("Baidu", request1, null));
			HttpRequestBase request2 = new HttpGet("http://cn.bing.com/");
			multiRequest.append(new Request("Bing", request2, null));

			MultiResponse multiResponse = httpPipline.query(multiRequest);
			List<Response> responses = multiResponse.getResponses();

			for (Response response : responses) {
				System.out.println(response.getRequestId());
				String body = EntityUtils.toString(response.getHttpResponse().getEntity(), Charsets.UTF_8);
				System.out.println(body);
			}
		} finally {
			if (httpPipline != null)
				httpPipline.close();
		}

	}

}

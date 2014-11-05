/**
 * 
 */
package org.ring.concurrenthttp.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HTTP;
import org.ring.concurrenthttp.entity.MultiRequest;
import org.ring.concurrenthttp.entity.MultiResponse;
import org.ring.concurrenthttp.entity.Request;
import org.ring.concurrenthttp.entity.Response;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:wangyuxuan@dangdang.com">Yuxuan Wang</a>
 *
 */
public class SimpleHttpPipline implements HttpPipline {

	private CloseableHttpAsyncClient httpClient;

	private ConnectionProfile connectionProfile;

	public SimpleHttpPipline(ConnectionProfile connectionProfile) {
		this.connectionProfile = connectionProfile;
		Preconditions.checkNotNull(connectionProfile);
		httpClient = HttpAsyncClients.custom()
		// TODO maybe add retry handler with httpasyncclient 4.1
				.setMaxConnTotal(connectionProfile.getMaxConnections()).setMaxConnPerRoute(connectionProfile.getMaxConnections()).build();
		httpClient.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ring.concurrenthttp.core.HttpPipline#query(org.ring.concurrenthttp
	 * .entity.MultiRequest)
	 */
	@Override
	public MultiResponse query(MultiRequest multiRequest) {
		final MultiResponse multiResponse = MultiResponse.create();
		final List<Request> requests = multiRequest.getRequests();
		if (requests != null) {
			final int count = requests.size();
			final CountDownLatch countDownLatch = new CountDownLatch(count);

			final Map<String, Future<HttpResponse>> futures = Maps.newHashMap();
			for (final Request request : requests) {
				renderRequest(request.getRequest(), request.getTimeout() > 0 ? request.getTimeout() : connectionProfile.getDefaultTimeout());
				final Future<HttpResponse> future = httpClient.execute(request.getRequest(), request.getContext(),
						new FutureCallback<HttpResponse>() {

							@Override
							public void failed(Exception ex) {
								countDownLatch.countDown();
							}

							@Override
							public void completed(HttpResponse result) {
								countDownLatch.countDown();
							}

							@Override
							public void cancelled() {
								countDownLatch.countDown();
							}
						});
				futures.put(request.getRequestId(), future);
			}

			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				// JUST INGORE
			}

			for (Entry<String, Future<HttpResponse>> future : futures.entrySet()) {
				try {
					multiResponse.append(new Response(future.getKey(), future.getValue().get()));
				} catch (InterruptedException e) {
					// JUST IGNORE;
				} catch (ExecutionException e) {
					throw Throwables.propagate(e);
				}
			}
		}
		return multiResponse;
	}

	/**
	 * 渲染请求
	 * 
	 * @param request
	 * @param timeout
	 */
	private void renderRequest(HttpRequestBase request, int timeout) {
		if (connectionProfile.isKeepAlive()) {
			request.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
		} else {
			request.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
		}

		request.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if (httpClient != null) {
			httpClient.close();
		}
	}

}

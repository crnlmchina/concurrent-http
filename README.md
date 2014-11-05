concurrent-http
===============

Tool to do http request in concurrent

			ConnectionProfile connectionProfile = new ConnectionProfile(2, 100, 1000);
			HttpPipline httpPipline = new SimpleHttpPipline(connectionProfile);

			MultiRequest multiRequest = MultiRequest.create();
			HttpRequestBase request1 = new HttpGet("http://www.baidu.com/");
			multiRequest.append(new Request("Baidu", request1, null));
			HttpRequestBase request2 = new HttpGet("http://cn.bing.com/");
			multiRequest.append(new Request("Bing", request2, null));

			MultiResponse multiResponse = httpPipline.query(multiRequest);
			List<Response> responses = multiResponse.getResponses();

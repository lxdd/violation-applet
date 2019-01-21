package com.auto.applet.demo;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.auto.applet.demo.model.Demo;

import reactor.core.publisher.Mono;

/**
 * 使用WebClient开发响应式Http客户端
 * 
 * @author li_xiaodong
 * @date 2018年6月1日
 */
public class WebClientTest {

	/**
	 * 1、创建WebClient对象并指定baseUrl； 2、HTTP GET； 3、异步地获取response信息； 4、将response
	 * body解析为字符串； 5、打印出来； 6、由于是异步的，我们将测试线程sleep
	 * 1秒确保拿到response，也可以像前边的例子一样用CountDownLatch。
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void webClientTest1() throws InterruptedException {
		WebClient webClient = WebClient.create("http://localhost:8090"); // 1
		Mono<String> resp = webClient.get().uri("/hello") // 2
				.retrieve() // 3
				.bodyToMono(String.class); // 4
		resp.subscribe(System.out::println); // 5
		TimeUnit.SECONDS.sleep(1); // 6
	}

	/**
	 * 1、这次我们使用WebClientBuilder来构建WebClient对象； 2、配置请求Header：Content-Type:
	 * application/stream+json；
	 * 3、获取response信息，返回值为ClientResponse，retrive()可以看做是exchange()方法的“快捷版”；
	 * 4、使用flatMap来将ClientResponse映射为Flux；
	 * 5、只读地peek每个元素，然后打印出来，它并不是subscribe，所以不会触发流；
	 * 6、上个例子中sleep的方式有点low，blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用。
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void webClientTest2() throws InterruptedException {
		WebClient webClient = WebClient.builder().baseUrl("http://localhost:8090").build(); // 1
		webClient.get().uri("/user").accept(MediaType.APPLICATION_STREAM_JSON) // 2
				.exchange() // 3
				.flatMapMany(response -> response.bodyToFlux(Demo.class)) // 4
				.doOnNext(System.out::println) // 5
				.blockLast(); // 6
	}

	/**
	 * /times，服务端推送
	 * 
	 * 1、配置请求Header：Content-Type: text/event-stream，即SSE；
	 * 2、这次用log()代替doOnNext(System.out::println)来查看每个元素；
	 * 3、由于/times是一个无限流，这里取前10个，会导致流被取消；
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void webClientTest3() throws InterruptedException {
		WebClient webClient = WebClient.create("http://localhost:8090");
		webClient.get().uri("/times").accept(MediaType.TEXT_EVENT_STREAM) // 1
				.retrieve().bodyToFlux(String.class).log() // 2
				.take(10) // 3
				.blockLast();
	}

	/**
	 * 1/声明速度为每秒一个MyEvent元素的数据流，不加take的话表示无限个元素的数据流；
	 * 2/声明请求体的数据格式为application/stream+json； 3/body方法设置请求体的数据。
	 */
//	@Test
//	public void webClientTest4() {
//		Flux<MyEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
//				.map(l -> new MyEvent(System.currentTimeMillis(), "message-" + l)).take(6); // 1
//		WebClient webClient = WebClient.create("http://localhost:8090");
//		webClient.post().uri("/events").contentType(MediaType.APPLICATION_STREAM_JSON) // 2
//				.body(eventFlux, MyEvent.class) // 3
//				.retrieve().bodyToMono(Void.class).block();
//	}

}

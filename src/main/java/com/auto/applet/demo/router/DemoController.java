package com.auto.applet.demo.router;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auto.applet.demo.handle.DemoHandler;
import com.auto.applet.demo.model.Demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

/**
 * Controller层
 * 
 * @author li_xiaodong
 * @date 2018年6月1日
 */
@RestController
@RequestMapping("/user")
public class DemoController {

	@Autowired
	private DemoHandler demoHandle;

	@PostMapping("")
	public Mono<Demo> save(Demo demo) {
		return this.demoHandle.save(demo);
	}

	@DeleteMapping("/{username}")
	public Mono<Long> deleteByUsername(@PathVariable String username) {
		return this.demoHandle.deleteByUsername(username);
	}

	@GetMapping("/{username}")
	public Mono<Demo> findByUsername(@PathVariable String username) {
		return this.demoHandle.findByUsername(username);
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Demo> findAll() {
		return this.demoHandle.findAll().delayElements(Duration.ofSeconds(2));
	}
	
	@RestController
	@RequestMapping("/violation/applet")
	public class SseController {
	    @GetMapping("/randomNumbers")
	    public Flux<ServerSentEvent<Integer>> randomNumbers() {
	        return Flux.interval(Duration.ofSeconds(1))
	                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
	                .map(data -> ServerSentEvent.<Integer>builder()
	                        .event("random")
	                        .id(Long.toString(data.getT1()))
	                        .data(data.getT2())
	                        .build());
	    }
	}
}
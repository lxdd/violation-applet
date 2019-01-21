package com.auto.applet.demo.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.auto.applet.demo.handle.TimeHandler;

@Configuration
public class TimeRouter {
	
	@Autowired
	private TimeHandler timeHandler;

	@Bean
	public RouterFunction<ServerResponse> timerRouter() {
		// 这种方式相对于上一行更加简洁
		return route(GET("/violation/applet/time"), req -> timeHandler.getTime(req))
				.andRoute(GET("/violation/applet/date"), timeHandler::getDate)
				.andRoute(GET("/violation/applet/times"), timeHandler::sendTimePerSec);
	}
}
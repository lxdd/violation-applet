package com.auto.applet.violation.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.auto.applet.violation.handle.ViolationHandler;

/**
 * 违章小程序Router
 * 
 * @author li_xiaodong
 * @date 2018年7月21日
 */
@Configuration
public class ViolationRouter {

	@Autowired
	private ViolationHandler handler;

	/**
	 * 接口如下：
	 * <p>
	 * 1、车辆列表
	 * <p>
	 * 2、绑定车辆
	 * <p>
	 * 3、解绑车辆
	 * <p>
	 * 4、查询违章
	 * <p>
	 * 5、行驶证识别
	 * <p>
	 * 6、是否可添加车辆
	 * 
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> router() {

		return route(GET("/violation/applet/list"), handler::listCar)
				.andRoute(POST("/violation/applet/car/bind"), handler::bindCar)
				.andRoute(DELETE("/violation/applet/car/unbind/{carId}"), handler::unBindCar)
				.andRoute(GET("/violation/applet/query/violation/{carId}"), handler::violation)
				.andRoute(POST("/violation/applet/driving/license"), handler::license)
				.andRoute(GET("/violation/applet/car/add/limit"), handler::limitAddCar);
	}

}
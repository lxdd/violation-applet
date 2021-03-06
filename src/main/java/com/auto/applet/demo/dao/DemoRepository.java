package com.auto.applet.demo.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.auto.applet.demo.model.Demo;

import reactor.core.publisher.Mono;

/**
 * DAO层repository 与非响应式Spring Data的CrudReposity对应的，响应式的Spring
 * Data也提供了相应的Repository库：ReactiveCrudReposity，当然，我们也可以使用它的子接口ReactiveMongoRepository
 * 
 * 1、ReactiveCrudRepository的泛型分别是User和ID的类型；
 * 2、ReactiveCrudRepository已经提供了基本的增删改查的方法，根据业务需要，我们增加四个方法（我们仅需按照规则定义接口方法名即可完成DAO层逻辑的开发）
 * 
 * @author li_xiaodong
 * @date 2018年6月1日
 */
public interface DemoRepository extends ReactiveCrudRepository<Demo, String> { // 1

	Mono<Demo> findByUsername(String username); // 2

	Mono<Long> deleteByUsername(String username);

}

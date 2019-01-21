package com.auto.applet.violation.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.auto.applet.violation.model.Limit;

import reactor.core.publisher.Flux;

/**
 * @author li_xiaodong
 * @date 2018年6月1日
 */
public interface LimitRepository extends ReactiveMongoRepository<Limit, String> {
	
	Flux<Limit> findByCSideUserIdAndIsDeleted(String cSideUserId, Integer isDeleted);
	
	

}

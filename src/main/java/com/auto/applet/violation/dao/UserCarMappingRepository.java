package com.auto.applet.violation.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.auto.applet.violation.model.UserCar;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author li_xiaodong
 * @date 2018年7月21日
 */
public interface UserCarMappingRepository extends  ReactiveMongoRepository<UserCar, String> { // 1

	/**
	 * @param cSideUserId
	 * @return
	 */
	Mono<UserCar> findByCSideUserId(String cSideUserId);
	
	
	/**
	 * @param cSideUserId
	 * @param carId
	 * @return
	 */
	Mono<UserCar> findByCSideUserIdAndCarIdAndIsDeleted(String cSideUserId, String carId, Integer isDeleted);
	
	
	
	Flux<UserCar> findByCSideUserIdAndIsDeleted(String cSideUserId, Integer isDeleted);
	
}

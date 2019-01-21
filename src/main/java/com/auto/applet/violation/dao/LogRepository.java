package com.auto.applet.violation.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.auto.applet.violation.model.Logs;

/**
 * @author li_xiaodong
 * @date 2018年6月1日
 */
public interface LogRepository extends ReactiveMongoRepository<Logs, String> { // 1

}

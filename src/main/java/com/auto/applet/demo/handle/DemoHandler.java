package com.auto.applet.demo.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto.applet.demo.dao.DemoRepository;
import com.auto.applet.demo.model.Demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 业务逻辑层
 * 
 * @author li_xiaodong
 * @date 2018年6月1日
 */
@Service
public class DemoHandler {

	@Autowired
	private DemoRepository demoRepository;

	/**
	 * 保存或更新。 如果传入的user没有id属性，由于username是unique的，在重复的情况下有可能报错，
	 * 这时找到以保存的user记录用传入的user更新它。
	 */
	public Mono<Demo> save(Demo demo) {
		return demoRepository.save(demo).onErrorResume(e -> // 1 onErrorResume进行错误处理
		demoRepository.findByUsername(demo.getUsername()) // 2 找到username重复的记录
				.flatMap(originalUser -> { // 4 由于函数式为User -> Publisher，所以用flatMap
					demo.setId(originalUser.getId());
					return demoRepository.save(demo); // 3 拿到ID从而进行更新而不是创建
				}));
	}

	public Mono<Long> deleteByUsername(String username) {
		return demoRepository.deleteByUsername(username);
	}

	public Mono<Demo> findByUsername(String username) {
		return demoRepository.findByUsername(username);
	}

	public Flux<Demo> findAll() {
		return demoRepository.findAll();
	}
}

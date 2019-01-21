package com.auto.applet.violation;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

	/**
	 * 日志
	 */
	protected final static Log logger = LogFactory.getLog(MonoFluxTest.class);

	/**
	 * 我有一个空序列，但是…​
	 * 
	 * 我想要一个缺省值来代替：defaultIfEmpty
	 * 
	 * 我想要一个缺省的序列来代替：switchIfEmpty
	 */
	@Test
	public void defaultIfEmpty() {

		// defaultIfEmpty 如果为空转换
		Mono.just("1") // 这个方法返回一个空的 Mono<String>
				.defaultIfEmpty("11").subscribe(s -> System.out.println(s));

		Mono.empty() // 这个方法返回一个空的 Mono<String>
				.defaultIfEmpty("11") // 将空序列转换为包含字符串 "" 的序列
				.subscribe(s -> System.out.println(s));

	}

	/**
	 * 
	 * 方法在源为空的情况下， 替换成另一个源
	 */
	@Test
	public static void switchIfEmpty() {

		Mono.just("1").switchIfEmpty(switchIfEmptyFallback()).subscribe(s -> System.out.println(s));

		// Mono.empty().switchIfEmpty(switchIfEmptyFallback()).subscribe(s ->
		// System.out.println(s));

	}

	public static Mono<? extends String> switchIfEmptyFallback() {

		System.out.println("incon");

		Mono.just("wozhixingleme").subscribe(s -> System.out.println(s));

		return Mono.just("111");
	}

	@Test
	public void simpleCreation() {

		List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");

		Flux<String> fewWords = Flux.just("Hello", "World");
		Flux<String> manyWords = Flux.fromIterable(words);

		fewWords.subscribe(System.out::println);
		System.out.println();
		manyWords.subscribe(System.out::println);
	}

	@Test
	public void findingMissingLetter() {

		List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");

		Flux<String> manyLetters = Flux.fromIterable(words).flatMap(word -> Flux.fromArray(word.split(""))).distinct()
				.sort()
				.zipWith(Flux.range(1, Integer.MAX_VALUE), (string, count) -> String.format("%2d. %s", count, string));

		manyLetters.subscribe(System.out::println);
	}
	
	@Test
	public void skipOrtakelast() {
		
		List<Integer> words = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,10);
		
		Flux<Integer> wordFlux = Flux.fromIterable(words);
		
		wordFlux.skipLast(1).subscribe(sg -> logger.info(sg));
		
	}

}

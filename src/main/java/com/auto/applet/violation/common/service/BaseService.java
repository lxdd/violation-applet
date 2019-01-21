package com.auto.applet.violation.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.auto.applet.violation.common.util.JsonUtil;

/**
 * 调用REST服务类的基类
 * 
 * @author li_xiaodong
 * @date 2018年7月9日
 * @param <T>
 */
public abstract class BaseService<T> {

	/**
	 * 日志
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Spring提供的用于访问Rest服务的客户端
	 */
	private RestTemplate restTemplate = new RestTemplate();

	/**
	 * GET
	 * 
	 * @param url
	 * @param vo
	 *            非必填
	 * @return
	 * @throws Exception
	 */
	public String get(String url) throws Exception {
		Object obj = null;
		return get(url, obj);
	}

	/**
	 * GET
	 * 
	 * @param url
	 * @param vo
	 *            用于记录调用日志
	 * @param urlVariables
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Object... urlVariables) throws Exception {
		// 日志记录begin
		long startTime = System.currentTimeMillis();
		logger.info("BaseService request url=" + url + "; request params: urlVariables=" + urlVariables);

		// HTTP GET
		String backResult;

		try {
			// HTTP GET
			backResult = restTemplate.getForObject(url, String.class, urlVariables);

		} catch (Exception e) {
			backResult = e.toString();
		}

		// 日志记录end
		long endTime = System.currentTimeMillis();
		logger.info("BaseService response url=" + url + "; response data:" + backResult + ";response time:"
				+ (endTime - startTime));

		return backResult;
	}

	/**
	 * POST
	 * 
	 * @param url
	 * @param dto
	 * @param vo
	 *            用于记录调用日志
	 * @return
	 * @throws Exception
	 */
	public String post(String url, T dto) throws Exception {

		// 日志记录begin
		long startTime = System.currentTimeMillis();
		logger.info("BaseService request url=" + url + "; request params: dto:" + JsonUtil.parseToJson(dto));

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<T> formEntity = new HttpEntity<T>(dto, headers);

		String backResult;

		try {
			// HTTP POST
			backResult = restTemplate.postForObject(url, formEntity, String.class);
		} catch (Exception e) {
			backResult = e.toString();
		}

		// 日志记录end
		long endTime = System.currentTimeMillis();
		logger.info("BaseService response url=" + url + "; response data:" + backResult + ";response time:"
				+ (endTime - startTime));

		return backResult;

	}
}

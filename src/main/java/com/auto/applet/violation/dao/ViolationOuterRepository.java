package com.auto.applet.violation.dao;

import com.auto.applet.violation.common.bo.*;
import com.auto.applet.violation.common.dto.ViolationRecord;
import com.auto.applet.violation.common.dto.ViolationResponse;
import com.auto.applet.violation.common.dto.ViolationResult;
import com.auto.applet.violation.common.support.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auto.applet.violation.common.service.BaseService;
import com.auto.applet.violation.common.util.JsonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 调用违章服务
 * 
 * @author li_xiaodong
 * @date 2018年7月22日
 */
@Component
public class ViolationOuterRepository extends BaseService<String> {

	/**
	 * 日志
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@Value("${violation.server.add}")
	String violationServerAdd;

	@Value("${violation.channelId}")
	String channelId;

	@Value("${violation.appId}")
	String appId;

	@Value("${violation.appKey}")
	String appKey;

	/**
	 * http://10.95.137.215:8081/blueone/chewu/violation/query/realtime?appId=51&
	 * appKey=ae92c1c6a59a47f19a8a4f282c6981b8&carEngine=131344&carPlate=%E5%86%
	 * 80A0000B&carVin=LFV5A24G0E3009124&userId=283159283699712
	 * 
	 * @param carEngine, carPlate, carVin, userId, carId
	 * @return
	 */
	public Result<ViolationBO> getViolation(String carEngine, String carPlate, String carVin, String userId, String carId) {

		Result<ViolationBO> result = new Result<>();
		ViolationResponse violationResponse = getViolationInner(carEngine, carPlate, carVin, userId);
		if (violationResponse == null) {
			result.setFail("-1", "未获取到查询结果");
			return result;
		}

		if (violationResponse.getErrno().equals("0")) {
			ViolationBO violationBO = new ViolationBO();

			ViolationResult violationResult = violationResponse.getData();

			// TODO 细分错误码
			Integer errCode = violationResult.getAutoPlatformErrCode();
			String errMsg = violationResult.getAutoPlatformErrMsg();
			if (errCode == null || errCode != 0) {

				// 过滤掉无用的非参数问题的错误信息
				if (errCode >= 10003 && errCode <= 10011 || errCode >= 10018) {
					errCode = 30001;
					errMsg = "无法查询";
				}
				result.setFail(errCode.toString(), errMsg);
				return result;
			}

			violationBO.setcSideUserId(userId);
			violationBO.setCarId(carId);
			violationBO.setNumber(violationResult.getCarNo());
			violationBO.setLastQueryTime(violationResult.getQueryTime());
			
			//用户最近一次查询违章的时间（一键查询+列表）
			violationBO.setGmtLatelyQuery(new Date());

			List<ViolationDetailBO> violationDetails = new ArrayList<>();
			int totalFine = 0;
			int totalDegree = 0;
			for (ViolationRecord item : violationResult.getViolationRecords()) {
				ViolationDetailBO violationDetailBO = new ViolationDetailBO();
				violationDetailBO.setDegree(item.getDegree());
				violationDetailBO.setFine(item.getFine());
				violationDetailBO.setViolationLocation(item.getViolationLocation());
				violationDetailBO.setViolationOccurTime(item.getViolationOccurTime());
				violationDetailBO.setViolationReason(item.getViolationReason());
				totalFine += item.getFine() == null ? 0 : item.getFine();
				totalDegree += item.getDegree() == null ? 0 : item.getDegree();
				violationDetails.add(violationDetailBO);
			}
			violationBO.setViolationCount(violationDetails.size());
			violationBO.setTotalFine(totalFine);
			violationBO.setTotalDegree(totalDegree);
			violationBO.setViolationDetails(violationDetails);

			result.setData(violationBO);
			return result;

		} else {
			// todo 细分错误原因
			result.setFail("-1", violationResponse.getErrmsg());
			return result;
		}
	}

	public ViolationResponse getViolationInner(Object... urlVariables) {

		// 组装url
		String url = String.format(violationServerAdd + String.format("?channelId=%s&appId=%s&appKey=%s", channelId, appId, appKey)
				+ "&carEngine=%s&carPlate=%s&carVin=%s&userId=%s", urlVariables);

		logger.info("ViolationOuterRepository getViolation url = " + url);

		String carViolationJsonStr = null;

		try {
			carViolationJsonStr = get(url);
		} catch (Exception e) {
			logger.error("调用违章服务异常。", e);
		}

		ViolationResponse violationResponse = null;

		try {
			if (carViolationJsonStr == null)
				return null;
			violationResponse = JsonUtil.parseToObject(carViolationJsonStr, ViolationResponse.class);
		} catch (Exception e) {
			logger.error("转换json对象异常。", e);
		}

		return violationResponse;
	}

}

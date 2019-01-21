package com.auto.applet.violation.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.auto.applet.violation.common.bo.LicenseBO;
import com.auto.applet.violation.common.constant.ReturnEnum;
import com.auto.applet.violation.common.dto.DrivingLicenseDTO;
import com.auto.applet.violation.common.dto.DrivingLicenseResponse;
import com.auto.applet.violation.common.service.BaseService;
import com.auto.applet.violation.common.support.Result;
import com.auto.applet.violation.common.util.JsonUtil;

/**
 * 行驶证识别
 * 
 * @author li_xiaodong
 * @date 2018年7月30日
 */
@Component
public class LicenseOuterRepository extends BaseService<String> {

	/**
	 * 日志
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@Value("${ocr.server.add}")
	String ocrServerAdd;

	public Result<LicenseBO> license(String licensePic) {

		// 构建返回
		Result<LicenseBO> result = new Result<>();
		LicenseBO bo = new LicenseBO();

		if (StringUtils.isEmpty(licensePic)) {
			result.setReturn(ReturnEnum.EMPTY_PARAM_LINCE);
			result.setData(bo);
			return result;
		}

		String url = ocrServerAdd + "?operType=1&picUrlList[0]=" + licensePic;

		DrivingLicenseResponse res = new DrivingLicenseResponse();

		String jsonStr = null;
		try {
			jsonStr = get(url);
		} catch (Exception e) {
			logger.error(ReturnEnum.OUTER_LINCE_SERVER_ERROR.getReturnMsg(), e);
			result.setReturn(ReturnEnum.OUTER_LINCE_SERVER_ERROR);
			result.setData(bo);
			return result;
		}

		if (StringUtils.isEmpty(jsonStr)) {
			result.setReturn(ReturnEnum.OUTER_LINCE_SERVER_RETURN_NULL);
			result.setData(bo);
			return result;
		}

		try {
			res = JsonUtil.parseToObject(jsonStr, DrivingLicenseResponse.class);
		} catch (Exception e) {
			logger.error(ReturnEnum.OUTER_LINCE_SERVER_RETURN_JSON_FAIL.getReturnMsg(), e);
			result.setReturn(ReturnEnum.OUTER_LINCE_SERVER_RETURN_JSON_FAIL);
			result.setData(bo);
			return result;
		}

		// 赋值
		DrivingLicenseDTO dto = res.getData();
		BeanUtils.copyProperties(dto, bo);
		bo.setEngine(dto.getEngineNumber());

		if (null == bo.getNumber() && null == bo.getEngine() && null == bo.getVin()) {

			result.setMsg(ReturnEnum.LINCE_OCR_EMPTY_FAIL.getReturnMsg());
		}

		result.setData(bo);
		return result;

	}

}

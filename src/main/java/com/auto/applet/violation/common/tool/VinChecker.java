package com.auto.applet.violation.common.tool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.auto.applet.violation.common.support.Result;

/**
 * VIN码有效性检工具类
 */
public class VinChecker {

	private static final int VIN_LEN = 17;
	private static final Map<String, Integer> valueWeightMap;
	static {
		Map<String, Integer> tmpMap = new HashMap<>();
		// 数字
		tmpMap.put("0", 0);
		tmpMap.put("1", 1);
		tmpMap.put("2", 2);
		tmpMap.put("3", 3);
		tmpMap.put("4", 4);
		tmpMap.put("5", 5);
		tmpMap.put("6", 6);
		tmpMap.put("7", 7);
		tmpMap.put("8", 8);
		tmpMap.put("9", 9);
		// 子母
		tmpMap.put("A", 1);
		tmpMap.put("B", 2);
		tmpMap.put("C", 3);
		tmpMap.put("D", 4);
		tmpMap.put("E", 5);
		tmpMap.put("F", 6);
		tmpMap.put("G", 7);
		tmpMap.put("H", 8);
		tmpMap.put("J", 1);
		tmpMap.put("K", 2);
		tmpMap.put("L", 3);
		tmpMap.put("M", 4);
		tmpMap.put("N", 5);
		tmpMap.put("P", 7);
		tmpMap.put("R", 9);
		tmpMap.put("S", 2);
		tmpMap.put("T", 3);
		tmpMap.put("U", 4);
		tmpMap.put("V", 5);
		tmpMap.put("W", 6);
		tmpMap.put("X", 7);
		tmpMap.put("Y", 8);
		tmpMap.put("Z", 9);
		valueWeightMap = Collections.unmodifiableMap(tmpMap);
	}

	private static final Map<Integer, Integer> idxWeightMap;
	static {
		Map<Integer, Integer> tmpMap = new HashMap<>();
		// 数字
		tmpMap.put(1, 8);
		tmpMap.put(2, 7);
		tmpMap.put(3, 6);
		tmpMap.put(4, 5);
		tmpMap.put(5, 4);
		tmpMap.put(6, 3);
		tmpMap.put(7, 2);
		tmpMap.put(8, 10);
		tmpMap.put(9, 0);
		tmpMap.put(10, 9);
		tmpMap.put(11, 8);
		tmpMap.put(12, 7);
		tmpMap.put(13, 6);
		tmpMap.put(14, 5);
		tmpMap.put(15, 4);
		tmpMap.put(16, 3);
		tmpMap.put(17, 2);
		idxWeightMap = Collections.unmodifiableMap(tmpMap);
	}

	public Result<String> checkVIN(String vin) {

		Result<String> resultData = new Result<>();

		if (vin.length() != VIN_LEN) {

			resultData.setCode("1001");
			resultData.setMsg("车架号长度不为17位");
			return resultData;

		}

		int total_score = 0;
		for (int i = 0; i < VIN_LEN; i++) {
			try {
				int value_score = valueWeightMap.get(vin.substring(i, i + 1));
				int idx_weight = idxWeightMap.get(i + 1);
				total_score += value_score * idx_weight;
			} catch (Exception e) {
				resultData.setCode("1002");
				resultData.setMsg("车架号格式错误：" + vin);
				return resultData;
			}
		}

		int check_bit_value = total_score % 11;
		String check_str;
		if (check_bit_value == 10) {
			check_str = "X";
		} else {
			check_str = String.valueOf(check_bit_value);
		}

		if (!(check_str.equals(vin.substring(8, 9)))) {
			resultData.setCode("1003");
			resultData.setMsg("车架号不正确");
			return resultData;
		}
		return resultData;

	}

	public static void main(String[] args) {

		String vin = "wf1ag292xdla50469";

		VinChecker vinChecker = new VinChecker();
		Result<String> resultData = vinChecker.checkVIN(vin);

		System.out.println(resultData);

	}

	

}

package com.mongodb.common;

import lombok.Data;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:23
 *
 * 响应数据
 */
@Data
public class ResponseData {


	/**
	 * 响应码
	 */
	private Integer code;

	/**
	 * 响应状态
	 */
	private Boolean success;

	/**
	 * 响应信息
	 */
	private String messages;

	public ResponseData(Integer code, Boolean success, String messages) {
		this.code = code;
		this.success = success;
		this.messages = messages;
	}
}

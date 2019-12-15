package com.mongodb.pojo;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 15:34
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志
 */
@Document(collection = "log_info")
@Data
public class LogInfo implements Serializable{

	@Id
	private String id;

	/**
	 * 日志执行状态
	 */
	private Boolean success;

	/**
	 * 日志信息
	 */
	private String msg;

	/**
	 * 具体操作
	 */
	private String action;

	/**
	 * 跟踪号
	 */
	private String trackingNo;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public LogInfo(Boolean success, String msg, String action, String trackingNo, Date createTime) {
		this.success = success;
		this.msg = msg;
		this.action = action;
		this.trackingNo = trackingNo;
		this.createTime = createTime;
	}
}

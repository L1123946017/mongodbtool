package com.mongodb.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:13
 */
@Document(collection = "tag_info")
public class TagInfo implements Serializable {

	@Id
	private String id;

	/**
	 * 标签名
	 */
	private String tagName;

	/**
	 * 标签描述
	 */
	private String tagDesc;

	/**
	 * 创建人
	 */
	private String createName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人
	 */
	private String updateName;

	/**
	 * 修改时间
	 */
	private String updateTime;

}

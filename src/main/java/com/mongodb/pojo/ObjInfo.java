package com.mongodb.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:07
 */
@Document(collection = "obj_info")
@Data
public class ObjInfo implements Serializable {

	@Id
	private String id;


	/**
	 * 对象id
	 */
	private String objId;


	/**
	 * 国家
	 */
	private String country;

	/**
	 * 标签名
	 */
	private String tagName;

	/**
	 * 标签值
	 */
	private String tagValue;

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
	private Date updateTime;


}

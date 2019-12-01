package com.mongodb.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author Jiahui Li
 * @DATE 2019/11/30 23:21
 */
@Document(collection = "user")
public class UserInfo implements Serializable {

	@Id
	private String id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 地址
	 */
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

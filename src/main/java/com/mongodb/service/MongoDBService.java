package com.mongodb.service;

import com.mongodb.pojo.UserInfo;

/**
 * @Author Jiahui Li
 * @DATE 2019/11/30 23:17
 */
public interface MongoDBService {
	void save(UserInfo userInfo) throws Exception;

	void del(String id) throws Exception;

	UserInfo selectById(String id) throws Exception;

	void updateById(UserInfo userInfo) throws Exception;

}

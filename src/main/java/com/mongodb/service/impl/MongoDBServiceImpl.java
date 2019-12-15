package com.mongodb.service.impl;

import com.mongodb.dao.MongoDBRepository;
import com.mongodb.pojo.UserInfo;
import com.mongodb.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author Jiahui Li
 * @DATE 2019/11/30 23:17
 */
@Service
public class MongoDBServiceImpl implements MongoDBService {


	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoDBRepository repository;

	/**
	 * 保存mongodb保存数据
	 *
	 * @param userInfo
	 */
	@Override
	public void save(UserInfo userInfo) {
		repository.save(userInfo);
	}


	/**
	 * 删除操作
	 *
	 * @param id
	 * @return
	 */
	@Override
	public void del(String id) {
		repository.deleteById(id);
	}

	/**
	 * 根据id 查询
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserInfo selectById(String id) throws Exception {
		return repository.findById(id).get();
	}


	/**
	 * 修改操作
	 *
	 * @param userInfo
	 * @throws Exception
	 */
	@Override
	public void updateById(UserInfo userInfo) throws Exception {
		mongoTemplate.save(userInfo);
		repository.save(userInfo);
	}
}

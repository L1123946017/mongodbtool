package com.mongodb.collector;

import com.mongodb.pojo.UserInfo;
import com.mongodb.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Jiahui Li
 * @DATE 2019/11/30 23:14
 */
@RestController
public class MongDBCollector {


	@Autowired
	private MongoDBService service;


	/**
	 * 插入操作
	 *
	 * @param userInfo
	 */
	@RequestMapping(value = "/api/mongodb/save", method = RequestMethod.POST)
	public String save(@RequestBody UserInfo userInfo) {

		String success = "成功";
		try {
			service.save(userInfo);
		} catch (Exception e) {
			success = "失败";
		}
		return success;
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/api/mongodb/del", method = RequestMethod.POST)
	public String del(String id) {
		String success = "成功";
		try {
			service.del(id);
		} catch (Exception e) {
			success = "失败";
		}
		return success;

	}


	/**
	 * 根据id查询数据
	 */
	@RequestMapping(value = "/api/mongodb/selectById", method = RequestMethod.GET)
	public UserInfo selectById(String id) {
		UserInfo userInfo = null;
		try {
			userInfo = service.selectById(id);
		} catch (Exception e) {
			userInfo = null;
		}
		return userInfo;
	}


	/**
	 * 更新
	 *
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/api/mongodb/updateById", method = RequestMethod.POST)
	public String updateById(@RequestBody UserInfo userInfo) {

		String success = "成功";
		try {
			service.updateById(userInfo);
		} catch (Exception e) {
			success = "失败";
		}
		return success;
	}
}


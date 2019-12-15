package com.mongodb.collector;

import com.mongodb.common.ResponseData;
import com.mongodb.pojo.ObjInfo;
import com.mongodb.service.ObjInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:19
 *
 * 对象表服务接口
 *
 */
@RestController
@RequestMapping("/api/obj")
public class ObjInfoCollector {


	@Autowired
	private ObjInfoService service;

	/**
	 * 数据更新操作  如果更新成功直接发送异步任务 告知对方
	 * @param objInfo
	 * @return
	 */
	@RequestMapping("/update")
	public ResponseData updateOption(@RequestBody ObjInfo objInfo){

		ResponseData data = null;

		try {
			service.updateOption(objInfo);
			data = new ResponseData(200,true,"更新成功");
		} catch (Exception e) {
			data = new ResponseData(400,false,"更新失败");
		}
		return data;

	}


}

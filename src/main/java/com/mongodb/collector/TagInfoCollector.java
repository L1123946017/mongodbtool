package com.mongodb.collector;

import com.mongodb.common.ResponseData;
import com.mongodb.service.TagInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:19
 *
 * 标签表服务接口
 *
 */
@RestController
@RequestMapping("/api/tag")
public class TagInfoCollector {

	private static final Logger logger = LoggerFactory.getLogger(TagInfoCollector.class);

	@Autowired
	private TagInfoService service;

	/**
	 * 此接口可以直接调用定时任务不需要等待指定时间
	 */
	@RequestMapping("/updateList")
	public void scheduledRun(){
		try {
			ResponseData data = service.scheduledRun();
			System.out.println(data);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}

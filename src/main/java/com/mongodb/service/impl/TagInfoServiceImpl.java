package com.mongodb.service.impl;

import com.mongodb.dao.TagInfoRepository;
import com.mongodb.pojo.TagInfo;
import com.mongodb.service.TagInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:22
 */
@Service
public class TagInfoServiceImpl implements TagInfoService {

	private static final Logger logger = LoggerFactory.getLogger(TagInfoServiceImpl.class);


	@Autowired
	private TagInfoRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 每天十点执行定时任务获取获取批量数据  获取前一天的数据
	 */
	@Scheduled(cron = "0 0 10 * * *")
	public void scheduledRun() {
		//当前时间
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		//把当前日期往前推一天
		calendar.set(calendar.DATE, calendar.get(calendar.DATE) - 1);
		Date afterDate = calendar.getTime();


		//根据当前时间和往前推一天时间之间，去批量的获取创建时间在这两个时间段的数据
		Query query = new Query();
		query.addCriteria(Criteria.where("createTime").gt(afterDate).lt(nowDate));
		List<TagInfo> tagInfos = mongoTemplate.find(query, TagInfo.class);
		if (CollectionUtils.isEmpty(tagInfos)) {
			logger.info("获取数据为空");
		}

		//保存数据
		tagInfos.stream().forEach(tagInfo -> repository.save(tagInfo));

	}

}

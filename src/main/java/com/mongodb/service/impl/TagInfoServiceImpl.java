package com.mongodb.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.common.ResponseData;
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
	public ResponseData scheduledRun() throws Exception{
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
			//不需要把数据发送对方
			logger.info("获取数据为空");
			throw new Exception("数据为空不需要发送");
		}

		logger.info("把数据推送给对方");

		//此处不知道数据发送是批量发送还是一条一条发送；
		// 执行批量发送
		ResponseData data = null;
		try {
			sendData(tagInfos);
			data = new ResponseData(200,true,"发送成功");
		} catch (Exception e) {
			data = new ResponseData(400,false,"发送失败");
			throw new Exception(JSON.toJSONString(data));
		}
		return data;
	}


	/**
	 * 执行数据发送操作
	 * @param infos
	 */
	public void sendData(List<TagInfo> infos) throws Exception{
		String uri = "http://www.baidu.com";
		try {
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true); // 设置可输入
			connection.setDoOutput(true); // 设置该连接是可以输出的
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));

			//把数据分装成json发送
			pw.write(objectMapper.writeValueAsString(JSON.toJSON(infos)));
			pw.flush();
			pw.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder result = new StringBuilder();
			while ((line = br.readLine()) != null) { // 读取数据
				result.append(line + "\n");
			}
			connection.disconnect();

			System.out.println(result.toString());
		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}
		logger.info("执行完成");
	}

}

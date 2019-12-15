package com.mongodb.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.dao.LogInfoRepository;
import com.mongodb.dao.ObjInfoRepository;
import com.mongodb.pojo.LogInfo;
import com.mongodb.pojo.ObjInfo;
import com.mongodb.service.ObjInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:22
 */
@Service
public class ObjInfoServiceImpl implements ObjInfoService {

	private static final Logger logger = LoggerFactory.getLogger(ObjInfo.class);


	@Autowired
	private ObjInfoRepository repository;

	@Autowired
	private LogInfoRepository logInfoRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	/*@Autowired
	private*/


	/**
	 * 数据更新成功之后 未出现异常直接发送 异步任务调用数据推送接口
	 *
	 * @param objInfo
	 */
	@Override
	public void updateOption(ObjInfo objInfo) throws Exception{
		LogInfo logInfo = null;
		//更新操作
		mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where("objId").is(objInfo.getObjId())
								.and("country").is(objInfo.getCountry())
								.and("tagName").is(objInfo.getTagName())),
				new Update().set("tagValue", objInfo.getTagValue()),
				ObjInfo.class);
		//以上操作不报错执行成功
		//如果不确定根据以上四个数据再次查询数据库
		ObjInfo info = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("objId").is(objInfo.getObjId())
						.and("country").is(objInfo.getCountry())
						.and("tagName").is(objInfo.getTagName())
						.and("tagValue").is(objInfo.getTagName()))
				, ObjInfo.class);
		//判断数据是否为空
		if (info == null) {
			logger.info("更新失败");
			//具体操作应该是一个枚举值，自己维护
			logInfo = new LogInfo(false,"更新失败","更新操作",objInfo.getObjId(),new Date());
			logInfoRepository.save(logInfo);

			//抛出异常 中断操作
			throw new Exception("更新失败");
		}


		//拿到更新数据执行异步发送操作
		logger.info("开始执行");
		/**
		 * 使用多线程执行两个异步任务
		 * 有两种方法
		 */

		/**
		 * 方法一 使用线程池
		 */
		/*Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				sendData(save);
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				sendData(save);
			}
		});
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,3,4000, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
		threadPoolExecutor.execute(thread1);
		threadPoolExecutor.execute(thread2);*/

		/**
		 * 方法二直接调起两个线程
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendData(info);
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				sendData(info);
			}
		}).start();

		logger.info("执行完成");

	}


	/**
	 * 异步任务发送数据
	 *
	 * @param data
	 */
	@Async
	public void sendData(ObjInfo data) {
		LogInfo logInfo = null;
		logger.info("异步任务发送数据开始");
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
			pw.write(objectMapper.writeValueAsString(JSON.toJSON(data)));
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
			logger.info("异步任务推送数据完成");

			//发送成功日志
			//.........
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("异步任务推送数据执行失败");
			//执行失败保存操作日志
			logInfo = new LogInfo(false,"发送失败","异步发送数据",data.getObjId(),new Date());
			logInfoRepository.save(logInfo);

		}
		logger.info("异步任务执行完成");
	}
}

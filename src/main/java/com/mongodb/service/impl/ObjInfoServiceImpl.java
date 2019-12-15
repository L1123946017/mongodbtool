package com.mongodb.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.dao.ObjInfoRepository;
import com.mongodb.pojo.ObjInfo;
import com.mongodb.service.ObjInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:22
 */
@Service
public class ObjInfoServiceImpl implements ObjInfoService {

	private static final Logger logger = LoggerFactory.getLogger(ObjInfo.class);


	@Autowired
	private ObjInfoRepository repository;


	/**
	 * 数据更新成功之后 未出现异常直接发送 异步任务调用数据推送接口
	 *
	 * @param objInfo
	 */
	@Override
	public void updateOption(ObjInfo objInfo) {
		//更新成功之后返回更新成功后的数据
		ObjInfo save = repository.save(objInfo);


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

			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				sendData(save);
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("异步任务推送数据执行失败");
		}


		logger.info("异步任务执行完成");
	}
}

package com.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/28 13:51
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {


	@Autowired
	private ThreadPoolProperties properties;

	@Bean(name = "taskExecutor")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(properties.getCorePoolSize());
		// 设置最大线程数
		executor.setMaxPoolSize(properties.getMaxPoolSize());
		// 设置队列容量
		executor.setQueueCapacity(properties.getQueueCapacity());
		// 设置线程活跃时间（秒）
		executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
		// 设置默认线程名称
		executor.setThreadNamePrefix(properties.getThreadNamePrefix());
		// 设置拒绝策略rejection-policy：当pool已经达到max size的时候，如何处理新任务 CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(properties.getShutdown());

		return executor;

	}

}

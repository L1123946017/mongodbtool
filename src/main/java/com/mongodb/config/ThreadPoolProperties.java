package com.mongodb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/28 13:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "thread-pool")
@PropertySource(value = {"classpath:application.yml"})
public class ThreadPoolProperties {

	private int corePoolSize;
	private int maxPoolSize;
	private int queueCapacity;
	private int keepAliveSeconds;
	private String threadNamePrefix;
	private Boolean shutdown;
}

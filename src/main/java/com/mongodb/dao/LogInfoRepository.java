package com.mongodb.dao;

import com.mongodb.pojo.LogInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 15:42
 */
public interface LogInfoRepository extends MongoRepository<LogInfo, String> {
}

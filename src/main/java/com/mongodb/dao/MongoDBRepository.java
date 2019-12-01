package com.mongodb.dao;

import com.mongodb.pojo.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Jiahui Li
 * @DATE 2019/11/30 23:21
 */
public interface MongoDBRepository extends MongoRepository<UserInfo, String> {


}

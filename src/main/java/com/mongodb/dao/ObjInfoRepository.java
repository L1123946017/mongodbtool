package com.mongodb.dao;

import com.mongodb.pojo.ObjInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:16
 */
public interface ObjInfoRepository extends MongoRepository<ObjInfo, String> {
}

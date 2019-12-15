package com.mongodb.dao;

import com.mongodb.pojo.TagInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:18
 */
public interface TagInfoRepository extends MongoRepository<TagInfo, String> {
}

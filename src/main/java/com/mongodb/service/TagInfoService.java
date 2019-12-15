package com.mongodb.service;

import com.mongodb.common.ResponseData;

/**
 * @Author Jiahui Li
 * @DATE 2019/12/15 13:21
 *
 * 标签数据处理服务
 */
public interface TagInfoService {


	ResponseData scheduledRun() throws Exception;
}

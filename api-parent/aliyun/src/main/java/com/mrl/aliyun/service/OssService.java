package com.mrl.aliyun.service;

import com.mrl.aliyun.vo.OssCallbackResponse;
import com.mrl.aliyun.vo.OssUploadParam;

/**
 * @ClassName: OssService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/12 0:06
 * @Version 1.0
 */
public interface OssService {

    /**
     * 返回上传所需的参数
     *
     * @return OssUploadParam
     */
    OssUploadParam getUploadParam();

    /**
     * 返回上传文件的url、大小等信息;未做访问限制
     *
     * @param response Oss回调时request的映射结果
     * @return OssCallbackResponse 处理了文件的访问路径
     */
    OssCallbackResponse callback(OssCallbackResponse response);
}

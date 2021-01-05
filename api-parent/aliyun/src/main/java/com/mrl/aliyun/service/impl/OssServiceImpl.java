package com.mrl.aliyun.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.mrl.aliyun.properties.OssProperties;
import com.mrl.aliyun.service.OssService;
import com.mrl.aliyun.vo.OssCallbackParam;
import com.mrl.aliyun.vo.OssCallbackResponse;
import com.mrl.aliyun.vo.OssUploadParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @ClassName: OssServiceImpl
 * @Description
 * @Author Mr.L
 * @Date 2020/12/12 0:10
 * @Version 1.0
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OssProperties properties;

    @Autowired
    private OSS oss;

    @Override
    public OssUploadParam getUploadParam() {
        String dir = properties.getDirPrefix() + DateUtil.today();
        Date expiration = new Date(System.currentTimeMillis() + properties.getPolicyExpire() * 1000);
        long maxSize = properties.getMaxSize() * 1024 * 1024;

        OssCallbackParam callbackParam = new OssCallbackParam();
        callbackParam.setCallbackUrl(properties.getCallback());
        callbackParam.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callbackParam.setCallbackBodyType("application/x-www-form-urlencoded");
        String callbackData64 = BinaryUtil.toBase64String(JSONUtil.parse(callbackParam).toString().getBytes(StandardCharsets.UTF_8));

        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        String postPolicy = oss.generatePostPolicy(expiration, policyConditions);
        String signature = oss.calculatePostSignature(postPolicy);
        String policy64 = BinaryUtil.toBase64String(postPolicy.getBytes(StandardCharsets.UTF_8));

        OssUploadParam uploadParam = new OssUploadParam();
        uploadParam.setOssaccessKeyId(properties.getAccessKeyId());
        uploadParam.setPolicy(policy64);
        uploadParam.setSignature(signature);
        uploadParam.setDir(dir);
        uploadParam.setHost("http://" + properties.getBucketName() + "." + properties.getEndpoint());
        uploadParam.setCallback(callbackData64);
        return uploadParam;
    }

    @Override
    public OssCallbackResponse callback(OssCallbackResponse response) {
        String filename = response.getFilename();
        if (filename != null) {
            response.setFilename("http://" + properties.getBucketName() + "." + properties.getEndpoint() + "/" + filename);
        }
        return response;
    }
}

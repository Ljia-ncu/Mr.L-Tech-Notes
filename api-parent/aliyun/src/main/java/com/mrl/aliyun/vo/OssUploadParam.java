package com.mrl.aliyun.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: OssUploadParam
 * @Description OssUploadController返回文件上传所需的参数
 * @Author Mr.L
 * @Date 2020/12/11 23:55
 * @Version 1.0
 */
@Setter
@Getter
public class OssUploadParam {
    @ApiModelProperty("OSS/RAM 访问控制KeyId")
    private String ossaccessKeyId;

    @ApiModelProperty("如大小、路径等策略限制,base64格式")
    private String policy;

    @ApiModelProperty("签名，包含有效期/policy")
    private String signature;

    /**
     * 前端接收dir,应该拼接UUID类型的fileName;
     */
    @ApiModelProperty("上传文件夹路径/")
    private String dir;

    @ApiModelProperty("bucket域名")
    private String host;

    /**
     * OssCallbackParam的Base64形式
     */
    @ApiModelProperty("上传成功的回调设置 ,base64格式")
    private String callback;
}

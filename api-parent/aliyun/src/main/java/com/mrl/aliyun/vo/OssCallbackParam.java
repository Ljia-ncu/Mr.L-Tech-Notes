package com.mrl.aliyun.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: OssCallbackParam
 * @Description 上传成功后，OSS会通过以下参数回调本服务器接口;
 * @Author Mr.L
 * @Date 2020/12/12 0:01
 * @Version 1.0
 */
@Setter
@Getter
public class OssCallbackParam {
    @ApiModelProperty("回调地址，如本服务器ip:port/oss/callback")
    private String callbackUrl;

    @ApiModelProperty("回调时传入request中的参数")
    private String callbackBody;

    @ApiModelProperty("回调时传入参数的格式")
    private String callbackBodyType;
}

package com.mrl.aliyun.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: OssCallbackResult
 * @Description OSS回调结果，映射OssCallbackParam.callbackBody中request内容
 * @Author Mr.L
 * @Date 2020/12/12 0:04
 * @Version 1.0
 */
@Setter
@Getter
public class OssCallbackResponse {
    @ApiModelProperty("文件名称")
    private String filename;

    @ApiModelProperty("文件大小")
    private String size;

    @ApiModelProperty("文件的mimeType")
    private String mimeType;

    @ApiModelProperty("图片文件的宽")
    private String width;

    @ApiModelProperty("图片文件的高")
    private String height;
}

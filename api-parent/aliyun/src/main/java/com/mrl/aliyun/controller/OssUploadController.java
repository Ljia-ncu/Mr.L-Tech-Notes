package com.mrl.aliyun.controller;

import com.mrl.aliyun.service.OssService;
import com.mrl.aliyun.vo.OssCallbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: OssUploadController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/12 13:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/aliyun/oss")
public class OssUploadController {

    @Autowired
    private OssService ossService;

    /**
     * application/x-www-form-urlencoded 不需要@RequestBody，否则会出错
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public ResponseEntity<?> callback(OssCallbackResponse ossCallbackResponse) {
        ossService.callback(ossCallbackResponse);
        return ResponseEntity.ok(ossCallbackResponse);
    }

    @RequestMapping(value = "/getUploadParam", method = RequestMethod.GET)
    public ResponseEntity<?> getUploadParam() {
        return ResponseEntity.ok(ossService.getUploadParam());
    }
}

package com.ljx.oss.controller;

import com.ljx.commonutils.R;
import com.ljx.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
// 解决跨域问题
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;


    // 上传头像方法、封面
    @PostMapping()
    public R uploadOssFile(MultipartFile file){
        // 获取上传文件
        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url", url);
    }

}

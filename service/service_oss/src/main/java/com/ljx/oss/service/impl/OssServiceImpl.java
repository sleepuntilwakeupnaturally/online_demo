package com.ljx.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.ljx.oss.service.OssService;
import com.ljx.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    // 上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.KET_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        // String objectName = "exampledir/exampleobject.txt";

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 1.为文件名称加一个随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            // 2.把文件按照日期分类
            // 获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 拼接
            fileName = datePath + "/" + fileName;

            // 调用oss方法实现上传
            /*
            * bucketName: Bucket名称
            * fileName: 上传到oss文件路径和文件名称
            * inputStream: 上传文件输入流
            * */
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient
            ossClient.shutdown();

            // 把上传之后文件路径放回
            // 需要把oss路径手动拼接
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            System.out.println(url);
            return url;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }




    }
}

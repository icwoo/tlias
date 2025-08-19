package com.hm.controller;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.hm.pojo.Result;
import com.hm.utils.AliyunOSSOperator;
import com.hm.utils.AliyunOSSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Description UploadController
 * @Author Lisheng Li
 * @Date 2025-08-15
 */
@RestController
@Slf4j
public class UploadController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;


    //@PostMapping("/upload")
    public Result handleFileUpload(MultipartFile file) throws Exception {
        log.info("文件上传，file:{}", file);

        //生成uuid,还可以把生成的随机数里的横杠“-”替换成别的，
        //String uuid = UUID.randomUUID().toString().replace("-","");
        UUID uuid = UUID.randomUUID();

        //获取文件名
        String originalFilename = file.getOriginalFilename();

        //截取文件名后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf('.'));

        //拼接新的文件
        String filePath = uuid + substring;

        //执行上传
        //file.transferTo(new File("D:\\4HeiMa",filePath));//这是本地的存储方法

        uploadOSS(filePath, file.getInputStream());//这是调用下面的private方法

        //返回结果
        return Result.success();
    }

    /**
     * 处理文件上传请求
     */
/*    @PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile file) throws Exception {

        log.info("username:{},age:{},上传文件：{}", username, age, file.getOriginalFilename());
        //调用工具类上传文件到阿里云oss，并返回文件路径
        String fileUrl = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());

        return Result.success(fileUrl);
    }  */
    @PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile file) throws Exception {

        log.info("username:{},age:{},上传文件：{}", username, age, file.getOriginalFilename());
        //调用工具类上传文件到阿里云oss，并返回文件路径
        String fileUrl = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());

        return Result.success(fileUrl);
    }


    //这是没封装的写法
    private void uploadOSS(String fileName, InputStream inputStream) throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写Bucket名称，例如examplebucket。记得改成自己的bucketName
        String bucketName = "elemed";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = fileName;
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
        String region = "cn-guangzhou";

        // 创建OSSClient实例。
        // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create().endpoint(endpoint).credentialsProvider(credentialsProvider).clientConfiguration(clientBuilderConfiguration).region(region).build();

        try {

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, " + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered " + "a serious internal problem while trying to communicate with OSS, " + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}

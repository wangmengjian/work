package com.nankingdata.yc.work.common;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUtils {
    private static String accessKey;
    private static String secretKey;
    private static String domain;
    private static String bucket;

    @Value("${upload.accessKey}")
    public void setAccessKey(String accessKey) {
        FileUtils.accessKey = accessKey;
    }
    @Value("${upload.secretKey}")
    public void setSecretKey(String secretKey) {
        FileUtils.secretKey = secretKey;
    }
    @Value("${upload.domain}")
    public void setDomain(String domain) {
        FileUtils.domain = domain;
    }
    @Value("${upload.bucket}")
    public void setBucket(String bucket) {
        FileUtils.bucket = bucket;
    }

    public static Map<String,Object> upload(MultipartFile file) throws Exception {
        Configuration cfg = new Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        System.out.println(accessKey+secretKey);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Map<String,Object> map=new HashMap<>();
        String key=Util.getVerifyRandom(12)+"/"+file.getOriginalFilename();
        Map<String,Object> result=new HashMap<>();
        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            result.put("key",key);
            result.put("filePath",domain+"/"+key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String getFullPath(String key){
        String result=null;
        if(key!=null){
            result=domain+"/"+key;
        }
        return result;
    }
    public static void delete(String url)throws Exception{
        String[] strings=url.split("/");
        strings[4]=URLDecoder.decode(strings[4],"utf-8");
        String key=strings[3]+"/"+strings[4];
        Configuration cfg = new Configuration(Zone.autoZone());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager=new BucketManager(auth,cfg);
        String bucket = "workspace";
        bucketManager.delete(bucket,key);
    }
}

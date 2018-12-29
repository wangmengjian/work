package com.nankingdata.yc.work.common;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;


public class FileUtils {
    private final static String accessKey = "bCNHpv29KW0PFzVtS4_TK5KKJYdWKawxiOO9_6xA";
    private final static String secretKey = "RBbeHJg04nNNF-Ah5kPSugpuIuj3-qXh00V3j__n";
    private final static String domain = "http://pjuzxdszq.bkt.clouddn.com/";

    public static String upload(MultipartFile file) throws Exception {
        Configuration cfg = new Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        String bucket = "workspace";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String key = Util.getVerifyRandom(12) + "/" + file.getOriginalFilename();
        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return domain+key;
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
    /*@Test
    public void test(){
        try {
            delete("http://pjuzxdszq.bkt.clouddn.com/2VjZ5203bKfr/123.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

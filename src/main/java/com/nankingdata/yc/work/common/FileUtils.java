package com.nankingdata.yc.work.common;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
}

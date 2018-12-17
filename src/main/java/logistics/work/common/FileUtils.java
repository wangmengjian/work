package logistics.work.common;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private final static String accessKey="bCNHpv29KW0PFzVtS4_TK5KKJYdWKawxiOO9_6xA";
    private final static String secretKey="RBbeHJg04nNNF-Ah5kPSugpuIuj3-qXh00V3j__n";
    private final static String domain="http://pjuzxdszq.bkt.clouddn.com/";
    public static List<String> upload(MultipartFile[] files,String dirName) throws Exception {
        Configuration cfg = new Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        String bucket="workspace";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        List<String> fileList=new ArrayList<>();
        for(MultipartFile file:files){
            String key=dirName+"/"+Util.getVerifyRandom(12)+"/"+file.getOriginalFilename();
            try {
                Response response = uploadManager.put(file.getInputStream(),key,upToken,null,null);
                fileList.add(domain+file.getOriginalFilename());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
        return fileList;
    }
    public static void download(HttpServletResponse response,String dirName)throws Exception{
        File root=new File(dirName);
        File[] files=root.listFiles();
        for(File file:files){
            try {
                BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(file));
                response.setHeader("Content-Disposition", "inline;filename="+file.getName());
                response.setContentType("application/octet-stream");
                BufferedOutputStream outputStream=new BufferedOutputStream(response.getOutputStream());
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer, 0, 1024)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
                outputStream.close();
                outputStream.flush();
            } catch (Exception e) {
            }
        }
    }
}

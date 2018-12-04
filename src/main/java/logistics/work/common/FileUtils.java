package logistics.work.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtils {
    public static String upload(MultipartFile[] files) throws Exception {
        File root = new File("G:/workFile/" +Util.getVerifyRandom(12) );
        root.mkdirs();
        for(MultipartFile file:files){
            File file1 = new File(root.getAbsolutePath() + "/" + file.getOriginalFilename());
            file1.createNewFile();
            file.transferTo(file1);
        }
        return root.getAbsolutePath();
    }
}

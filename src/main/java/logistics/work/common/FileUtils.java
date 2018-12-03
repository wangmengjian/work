package logistics.work.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtils {
    public static String upload(MultipartFile file) throws Exception {
        File root = new File("G:/workFile/" +Util.getVerifyRandom(12) );
        root.mkdirs();
        FileOutputStream fileOutputStream = null;
        File file1 = new File(root.getAbsolutePath() + "/" + file.getOriginalFilename());
        file1.createNewFile();
        fileOutputStream = new FileOutputStream(file1);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return root.getAbsolutePath();
    }
}

package logistics.work.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtils {
    public static String upload(MultipartFile file, String begin) throws Exception {
        File root = new File("G:/workFile/" + begin + System.currentTimeMillis());
        root.mkdirs();
        FileOutputStream fileOutputStream = null;
        File file1 = new File(root.getAbsolutePath() + "/" + file.getOriginalFilename());
        file1.createNewFile();
        System.out.println(file1.getAbsolutePath());
        fileOutputStream = new FileOutputStream(file1);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return root.getAbsolutePath();
    }
}

package logistics.work.common;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

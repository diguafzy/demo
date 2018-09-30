package com.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class);

    public static String getClasspath(){
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim(); 
        if(path.indexOf(":") != 1){
            path = File.separator + path;
        }
        return path;
    }
    
    /**
     * @param file          //文件对象
     * @param filePath      //上传路径
     * @param fileName      //文件名
     * @return  文件名
     */
    public static String fileUp(MultipartFile file, String filePath, String fileName){
        String extName = ""; // 扩展名格式：
        try {
            if (file.getOriginalFilename().lastIndexOf(".") >= 0){
                extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return fileName+extName;
    }

    /**
     * 写文件到当前目录的upload目录中
     * 
     * @param in
     * @param fileName
     * @throws IOException
     */
    public static String copyFile(InputStream in, String dir, String realName)
            throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileUtils.copyInputStreamToFile(in, file);
        return realName;
    }
    
    /**
     * 读取到字节数组2
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filePath) {

        File f = new File(filePath);
        if (!f.exists()) {
            logger.error(new FileNotFoundException(filePath).getMessage());
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            try {
                fs.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }
}

package com.fall.robok.util;

import com.fall.robok.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author FAll
 * @date 2022/9/25 21:19
 */
@Component
public class MultipartFileUpload {

    @Autowired
    ServerConfig serverConfig;

    public String[] uploadFile(String[] fileNames, MultipartFile[] files) {
        String destination = serverConfig.getPath();
        String urls[] = new String[fileNames.length];
        for (int i = 0; i < files.length; i++) {
            try {
                String wholeFileName = fileNames[i] + ".jpg";

                byte[] bytes = files[i].getBytes();
                OutputStream out = Files.newOutputStream(Paths.get(destination + wholeFileName));
                out.write(bytes);
                out.close();

                urls[i] = wholeFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return urls;
    }

}

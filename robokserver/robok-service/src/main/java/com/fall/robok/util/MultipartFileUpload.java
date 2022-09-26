package com.fall.robok.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author FAll
 * @date 2022/9/25 21:19
 */
public class MultipartFileUpload {

    public static Boolean uploadFile(String[] fileNames, MultipartFile[] files) {
        for (int i = 0; i < files.length; i++) {
            try {
                files[i].transferTo(new File("C:\\Users\\94031\\Desktop\\img\\"
                        + fileNames[i]
                        + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}

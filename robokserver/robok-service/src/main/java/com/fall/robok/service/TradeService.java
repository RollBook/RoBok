package com.fall.robok.service;

import com.fall.robok.util.MultipartFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author FAll
 * @date 2022/9/25 20:31
 */

@Service
public class TradeService {

    public Boolean addBook(MultipartFile[] photo, String bookName) {
        String[] bookNames = {bookName};
        MultipartFileUpload.uploadFile(bookNames, photo);

        return true;

    }
}

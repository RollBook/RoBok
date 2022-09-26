package com.fall.robok.service;

import com.fall.robok.mapper.BookMapper;
import com.fall.robok.model.Book;
import com.fall.robok.util.MultipartFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author FAll
 * @date 2022/9/25 20:31
 */

@Service
public class TradeService {

    @Autowired
    BookMapper bookMapper;

    public Boolean setImg(MultipartFile[] photo, String bookName) {
        String[] bookNames = {bookName};
        Boolean ret = MultipartFileUpload.uploadFile(bookNames, photo);

        return ret;
    }

    /**
     * @param book
     * @author FAll
     * @description 添加书本
     * @return: java.lang.Boolean
     * @date 2022/9/26 22:52
     */
    public Boolean addBook(Book book) {
        Integer ret = bookMapper.addBook(book);
        if (ret == 0) {
            return false;
        }
        return true;
    }
}

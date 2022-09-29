package com.fall.robok.service;

import com.fall.robok.Config.ServerConfig;
import com.fall.robok.mapper.BookMapper;
import com.fall.robok.model.Book;
import com.fall.robok.util.MultipartFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;


/**
 * @author FAll
 * @date 2022/9/25 20:31
 */

@Service
public class TradeService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    MultipartFileUpload multipartFileUpload;

    @Autowired
    ServerConfig serverConfig;

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


    /**
     * @param openid
     * @param timestamp
     * @param rank
     * @param photo
     * @author FAll
     * @description 保存书本图片，生成url
     * @return: java.lang.String
     * @date 2022/9/27 14:45
     */
    public Boolean setImg(String openid, String timestamp, String rank, MultipartFile[] photo) {

        String[] names = {openid + timestamp + rank};
        String[] url = multipartFileUpload.uploadFile(names, photo);
        if (url == null) {
            return null;
        }
        String retUrl = url[0];
        if (serverConfig.getEnvironment().equals("dev")) {
            retUrl = ("http://localhost:" + serverConfig.getPort() + "/api/") + url[0];
        }
        Book book = new Book();
        book.setOpenId(openid);
        book.setTimestamp(timestamp);
        switch (Integer.parseInt(rank)) {
            case 1: {
                book.setUrl1(retUrl);
                break;
            }
            case 2: {
                book.setUrl2(retUrl);
            }
            case 3: {
                book.setUrl3(retUrl);
            }
        }

        Integer ret = bookMapper.updateBook(book);

        if (ret == 0) {
            return false;
        }
        return true;
    }
}

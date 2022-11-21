package com.fall.robok.service;

import com.fall.robok.model.Book;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author FAll
 * @date 2022/11/21 10:27
 */
public interface ITradeService {

    /**
     * @param book
     * @author FAll
     * @description 添加书本
     * @return: java.lang.Boolean
     * @date 2022/11/21 10:37
     */
    Boolean addBook(Book book);

    /**
     * @param openid
     * @param timestamp
     * @param rank
     * @param photo
     * @author FAll
     * @description 保存书本图片，生成url
     * @return: java.lang.Boolean
     * @date 2022/11/21 10:37
     */
    Boolean setImg(String openid, String timestamp, String rank, MultipartFile[] photo);
}

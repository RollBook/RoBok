package com.fall.robok.service;

import com.fall.robok.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author FAll
 * @date 2022/11/21 10:27
 */
public interface ISellerService {

    /**
     * @param book 书本
     * @author FAll
     * @description 添加书本
     * @return: java.lang.Boolean
     * @date 2022/11/21 10:37
     */
    Boolean addBook(Book book);

    /**
     * @param openid openid
     * @param timestamp 13位时间戳
     * @param rank 图片排序
     * @param photo 图片
     * @author FAll
     * @description 保存书本图片，生成url
     * @return: java.lang.Boolean
     * @date 2022/11/21 10:37
     */
    Boolean setImg(String openid, String timestamp, String rank, MultipartFile[] photo);

    /**
     * @author Tan
     * @description
     * @param openid openid
     * @return: java.util.List<com.fall.robok.model.Book>
     * @date  15:33
     */
    List<Book> getSellBook(String openid);
}

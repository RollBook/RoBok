package com.fall.robok.service;

import com.fall.robok.po.Book;
import com.fall.robok.vo.SellerInfo;
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
     * @author FAll
     * @description 更新卖家信息
     * @param sellerInfo 卖家信息
     * @param openid openid
     * @return: java.lang.Boolean
     * @date 2023/3/18 15:35
     */
    Boolean setSellerInfo(SellerInfo sellerInfo,String openid);

    /**
     * @author Tan
     * @description 卖书书架，获取书本
     * @param openid openid
     * @return: java.util.List<com.fall.robok.po.Book>
     * @date  15:33
     */
    List<Book> getSellBook(String openid);
}

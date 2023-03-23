package com.fall.robok.service;

import com.fall.robok.po.Book;
import com.fall.robok.vo.BookOfSeller;
import com.fall.robok.vo.SellerInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author FAll
 * @date 2022/11/21 10:27
 */
public interface ISellerService {

    /**
     * @param books 待上架书本
     * @author FAll
     * @description 卖家上架书本
     * @return: java.lang.Boolean
     * @date 2022/11/21 10:37
     */
    Boolean addSellerBooks(BookOfSeller[] books);

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
     * @description 获取卖家信息
     * @param openid openid
     * @return: com.fall.robok.vo.SellerInfo
     * @date 2023/3/18 17:23
     */
    SellerInfo getSellerInfo(String openid);

    /**
     * @author FAll
     * @description 更新卖家信息
     * @param sellerInfo 卖家信息
     * @param openid openid
     * @return: java.lang.Boolean
     * @date 2023/3/18 15:35
     */
    Boolean setSellerInfo(SellerInfo sellerInfo,String openid);


}

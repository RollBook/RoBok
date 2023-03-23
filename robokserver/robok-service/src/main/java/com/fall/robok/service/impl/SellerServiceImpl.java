package com.fall.robok.service.impl;

import com.fall.robok.config.ServerConfig;
import com.fall.robok.constant.AuditStatus;
import com.fall.robok.mapper.BookMapper;
import com.fall.robok.mapper.SellerMapper;
import com.fall.robok.mapper.UserMapper;
import com.fall.robok.po.Book;
import com.fall.robok.po.User;
import com.fall.robok.service.ISellerService;
import com.fall.robok.util.file.MultipartFileUpload;
import com.fall.robok.vo.BookOfSeller;
import com.fall.robok.vo.SellerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author FAll
 * @date 2022/9/25 20:31
 */

@Service
public class SellerServiceImpl implements ISellerService {

    private final BookMapper bookMapper;

    private final UserMapper userMapper;

    private final SellerMapper sellerMapper;

    private final MultipartFileUpload multipartFileUpload;

    private final ServerConfig serverConfig;

    @Autowired
    public SellerServiceImpl(MultipartFileUpload multipartFileUpload,
                             SellerMapper sellerMapper,UserMapper userMapper,
                             BookMapper bookMapper,ServerConfig serverConfig) {
        this.multipartFileUpload = multipartFileUpload;
        this.sellerMapper = sellerMapper;
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
        this.serverConfig = serverConfig;
    }


    /**
     * @param books 待上架书本
     * @author FAll
     * @description 添加书本
     * @return: java.lang.Boolean
     * @date 2022/9/26 22:52
     */
    @Override
    public Boolean addSellerBooks(BookOfSeller[] books) {
        for (BookOfSeller bookOfSeller : books) {
            String bookId = bookOfSeller.getOpenId()
                    +"&&"
                    + bookOfSeller.getTimestamp();
            Book book = new Book.Builder()
                                .bookId(bookId)
                                .audit(AuditStatus.UNCHECKED.getStatus())
                                .bookOfSeller(bookOfSeller)
                                .build();
            if(bookMapper.addBook(book) == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param openid openid
     * @param timestamp 13位时间戳
     * @param rank 图片排序
     * @param photo 图片
     * @author FAll
     * @description 保存书本图片，生成url
     * @return: java.lang.String
     * @date 2022/9/27 14:45
     */
    @Override
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
                break;
            }
            case 3: {
                book.setUrl3(retUrl);
                break;
            }
        }

        Integer ret = bookMapper.updateBook(book);

        return ret != 0;
    }

    @Override
    public SellerInfo getSellerInfo(String openid) {
        return sellerMapper.getSellerInfoByOpenId(openid);
    }

    /**
     * @author FAll
     * @description 更新卖家信息
     * @param sellerInfo 卖家信息
     * @param openid openid
     * @return: java.lang.Boolean
     * @date 2023/3/18 15:42
     */
    @Override
    public Boolean setSellerInfo(SellerInfo sellerInfo,String openid) {
         int ret = userMapper.updateByOpenId(new User.Builder()
                                                     .openId(openid)
                                                     .seller(sellerInfo)
                                                     .build());
        return ret != 0;
    }

}

package com.fall.robok.controller;

import com.fall.robok.model.Book;
import com.fall.robok.service.impl.TradeServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


/**
 * @author FAll
 * @date 2022/9/25 20:25
 */

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    TradeServiceImpl tradeService;

    /**
     * @param bookName
     * @param openid
     * @param bookStatus
     * @param timeStamp
     * @param bookPrice
     * @param bookInfo
     * @author FAll
     * @description 添加书本
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/26 22:46
     */
    @ApiOperation("创建书本:添加书本第一步")
    @PostMapping("/add_book")
    public ResBean addBook(@RequestParam("bookName") String bookName, @RequestParam("openid") String openid
            , @RequestParam("bookStatus") String bookStatus, @RequestParam("timeStamp") String timeStamp
            , @RequestParam("bookPrice") Double bookPrice, @RequestParam("bookInfo") String bookInfo) {

        Boolean ret = tradeService.addBook(new Book(UUID.randomUUID().toString(), openid,
                bookName, bookPrice, null, bookStatus, 0,
                null, null, null, null, bookInfo, timeStamp));
        if (!ret) {
            return ResBean.badRequest("Bad request");
        }
        return ResBean.ok("ok");
    }


    /**
     * @param openid
     * @param timeStamp
     * @param rank
     * @param photo
     * @author FAll
     * @description 保存书本图片，生成并存储url
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/27 14:19
     */
    @ApiOperation("保存书本图片，生成并存储url:添加书本第二步")
    @PostMapping("set_img")
    public ResBean setImg(@RequestPart("openid") String openid, @RequestPart("timeStamp") String timeStamp,
                          @RequestPart("rank") String rank, @RequestPart("files") MultipartFile[] photo) {
        Boolean ret = tradeService.setImg(openid, timeStamp, rank, photo);

        if (!ret) {
            return ResBean.badRequest("Bad request");
        }
        return ResBean.ok("ok");
    }

}

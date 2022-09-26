package com.fall.robok.controller;

import com.fall.robok.model.Book;
import com.fall.robok.model.ResBean;
import com.fall.robok.service.TradeService;
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
    TradeService tradeService;

    /**
     * @param bookName
     * @param openid
     * @param bookStatus
     * @param timeStamp
     * @param bookPrice
     * @param bookInfo
     * @author FAll
     * @description 添加书本
     * @return: com.fall.robok.model.ResBean
     * @date 2022/9/26 22:46
     */
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


    public ResBean setImg(@RequestPart("user") String user, @RequestPart("files") MultipartFile[] photo){

        return ResBean.ok("ok");
    }
    // tradeService.addBook(photo, "1");

}
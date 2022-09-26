package com.fall.robok.controller;

import com.fall.robok.model.ResBean;
import com.fall.robok.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author FAll
 * @date 2022/9/25 20:25
 */

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    TradeService tradeService;

    @PostMapping("/add_book")
    public ResBean addBook(@RequestPart("user") String user, @RequestPart("files") MultipartFile[] photo) {

        tradeService.addBook(photo, "1");

        return ResBean.ok("ok");
    }

}

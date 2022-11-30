package com.fall.robok.controller;

import com.fall.robok.model.Book;
import com.fall.robok.service.impl.SellerServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


/**
 * @author FAll
 * @date 2022/9/25 20:25
 */

@Validated
@RestController
@RequestMapping("/trade")
public class SellerController {

    @Autowired
    SellerServiceImpl tradeService;

    /**
     * @param bookName
     * @param openid
     * @param bookStatus
     * @param timeStamp
     * @param bookPrice
     * @param bookInfo
     * @param response
     * @author FAll
     * @description 添加书本
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/26 22:46
     */
    @ApiOperation("创建书本:添加书本第一步")
    @PostMapping("/add_book")
    public ResBean addBook(@NotEmpty(message = "书本名不能为空") @RequestParam("bookName") String bookName,
                           @NotEmpty(message = "openid不能为空") @RequestParam("openid") String openid,
                           @NotEmpty(message = "状态信息不能为空") @RequestParam("bookStatus") String bookStatus,
                           @NotEmpty(message = "创建时间不能为空") @RequestParam("timeStamp") String timeStamp,
                           @NotNull(message = "价格不能为空") @RequestParam("bookPrice") Double bookPrice,
                           @RequestParam("bookInfo") String bookInfo,
                           HttpServletResponse response) {

        Boolean ret = tradeService.addBook(new Book(UUID.randomUUID().toString(), openid,
                bookName, bookPrice, null, bookStatus, 0,
                null, null, null, null, bookInfo, timeStamp));
        if (!ret) {
            response.setStatus(405);
            return ResBean.badRequest("Bad request");
        }

        response.setStatus(201);
        return ResBean.ok("ok");
    }


    /**
     * @param openid
     * @param timeStamp
     * @param rank
     * @param photo
     * @param response
     * @author FAll
     * @description 保存书本图片，生成并存储url
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/27 14:19
     */
    @ApiOperation("保存书本图片，生成并存储url:添加书本第二步")
    @PostMapping("set_img")
    public ResBean setImg(@NotEmpty @RequestPart("openid") String openid,
                          @NotEmpty @RequestPart("timeStamp") String timeStamp,
                          @NotEmpty @RequestPart("rank") String rank,
                          @NotNull @RequestPart("files") MultipartFile[] photo,
                          HttpServletResponse response) {
        Boolean ret = tradeService.setImg(openid, timeStamp, rank, photo);

        if (!ret) {
            response.setStatus(405);
            return ResBean.badRequest("Bad request");
        }
        response.setStatus(201);
        return ResBean.ok("ok");
    }

}

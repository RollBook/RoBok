package com.fall.robok.controller;

import com.fall.robok.po.Book;
import com.fall.robok.service.impl.SellerServiceImpl;
import com.fall.robok.util.bean.ResBean;
import com.fall.robok.vo.SellerInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


/**
 * @author FAll
 * @date 2022/9/25 20:25
 */

@Validated
@RestController
@RequestMapping("/seller")
public class SellerController {

    private final SellerServiceImpl sellerService;

    @Autowired
    public SellerController(SellerServiceImpl sellerService) {
        this.sellerService=sellerService;
    }

    /**
     * @param bookName 书本名称
     * @param openid 用户openid
     * @param bookStatus 书本状态
     * @param timeStamp 13位时间戳
     * @param bookPrice 书本价格
     * @param bookInfo 书本信息
     * @param response http响应
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

        Boolean ret = sellerService.addBook(new Book(UUID.randomUUID().toString(), openid,
                bookName, bookPrice, null, bookStatus, 0,
                null, null, null, null, bookInfo, timeStamp));
        if (!ret) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ResBean.badRequest("Bad request");
        }

        return ResBean.ok("ok");
    }


    /**
     * @param openid openid
     * @param timeStamp 13位时间戳
     * @param rank 图片排序
     * @param photo 图片
     * @param response http响应
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
        Boolean ret = sellerService.setImg(openid, timeStamp, rank, photo);

        if (!ret) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ResBean.badRequest("Bad request");
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return ResBean.ok("ok");
    }

    @GetMapping("/get_seller_info")
    public ResBean getSellerInfo(HttpServletRequest request,HttpServletResponse response) {
        SellerInfo ret = sellerService.getSellerInfo(request.getHeader("openid"));
        if(ret != null) {
            return ResBean.ok("ok",ret);
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return ResBean.notFound();
    }

    @ApiOperation("更新卖家信息")
    @PostMapping("/set_seller_info")
    public ResBean setSellerInfo(@RequestBody SellerInfo sellerInfo, HttpServletRequest request){
        String openid = request.getHeader("openid");
        Boolean ret = sellerService.setSellerInfo(sellerInfo,openid);
        if(ret) {
            return ResBean.ok("ok");
        }
        return ResBean.badRequest("badRequest");

    }

}

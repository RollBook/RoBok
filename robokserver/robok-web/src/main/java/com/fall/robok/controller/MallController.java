package com.fall.robok.controller;

import com.fall.robok.po.Book;
import com.fall.robok.po.Cart;
import com.fall.robok.po.MallSwiper;
import com.fall.robok.service.impl.MallServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/13,
 * @time 17:23,
 */
@RestController
@RequestMapping("/mall")
public class MallController {

    public final MallServiceImpl mallService;

    @Autowired
    public MallController(MallServiceImpl mallService){
        this.mallService = mallService;
    }

    /**
     * @author Tan
     * @description 商城轮播图获取
     * @param
     * @return: com.fall.robok.util.bean.ResBean
     * @date  21:02
     */
    @ApiOperation("商城轮播图获取")
    @GetMapping("/get_mall_swiper")
    public ResBean getMallSwiper(){
        List<MallSwiper> mallSwiper = mallService.getMallSwiper();
        return ResBean.ok("ok",mallSwiper);

    }

    /**
     * @author Tan
     * @description 获取商品列表
     * @param nowPage
     * @return: com.fall.robok.util.bean.ResBean
     * @date  17:56
     */
    @ApiOperation("获取商品列表")
    @GetMapping("/get_goods")
    public  ResBean getGoods(@NotEmpty @RequestParam("nowPage") Integer nowPage){
        List<Book> goods = mallService.getGoods(nowPage);
//        System.out.println(goods);
        return ResBean.ok("ok",goods);
    }

    /**
     * @author Tan
     * @description 通过名字搜索书本
     * @param input
     * @return: com.fall.robok.util.bean.ResBean
     * @date  20:17
     */
    @ApiOperation("通过名字搜索书本")
    @GetMapping("/search_goods")
    public ResBean searchGoods(@NotEmpty @RequestParam("input") String input){
        List<Book> goods = mallService.searchBooks(input);
        return ResBean.ok("ok",goods);
    }

    /**
     * @author Tan
     * @description 通过完好度搜索书本
     * @param input
     * @return: com.fall.robok.util.bean.ResBean
     * @date  20:18
     */
    @ApiOperation("通过完好度搜索书本")
    @GetMapping("/search_goods_how")
    public ResBean searchGoodsByHow(@NotEmpty @RequestParam("input") String input){
        List<Book> goods = mallService.searchBooksByHow(input);
        return ResBean.ok("ok",goods);
    }

    /**
     * @author Tan
     * @description 通过价格搜索书本
     * @param input
     * @return: com.fall.robok.util.bean.ResBean
     * @date  20:18
     */
    @ApiOperation("通过价格搜索书本")
    @GetMapping("/search_goods_price")
    public ResBean searchGoodsByPrice(@NotEmpty @RequestParam("input") String input){
        List<Book> goods = mallService.searchBooksByPrice(input);
        return ResBean.ok("ok",goods);
    }

    /**
     * @author Tan
     * @description 通过id搜索书本
     * @param bookId
     * @return: com.fall.robok.util.bean.ResBean
     * @date  10:22
     */
    @ApiOperation("通过id搜索书本")
    @GetMapping("/get_good_id")
    public ResBean getBookById(@NotEmpty @RequestParam("bookId") String bookId){
        Book good = mallService.getGoodById(bookId);
        return ResBean.ok("ok",good);
    }


    /**
     * @author Tan
     * @description 加入购物车
     * @param cart
     * @return: com.fall.robok.util.bean.ResBean
     * @date  10:32
     */
    @ApiOperation("加入购物车")
    @PostMapping("/add_cart")
    public ResBean addCart(@RequestBody Cart cart){
        int m = mallService.addCart(cart);
        return ResBean.ok("ok",m);
    }

}

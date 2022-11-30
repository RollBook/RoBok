package com.fall.robok.controller;

import com.fall.robok.service.impl.RollBookServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;


/**
 * @author FAll
 * @date 2022/9/24 14:37
 */

@RestController
@RequestMapping("/rollbook")
public class RollBookController {

    @Autowired
    RollBookServiceImpl rollBookService;

    /**
     * @param response
     * @author FAll
     * @description 获取首页轮播图
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/24 15:45
     */
    @ApiOperation("获取小程序首页轮播图")
    @GetMapping("/get_index_swiper")
    public ResBean getIndexSwiper(HttpServletResponse response) {
        response.setStatus(200);
        return ResBean.ok("ok", rollBookService.getAllIndexSwiper());
    }


}

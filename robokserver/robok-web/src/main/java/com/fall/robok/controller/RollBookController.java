package com.fall.robok.controller;

import com.fall.robok.service.impl.RollBookServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
     * @param
     * @author FAll
     * @description 获取首页轮播图
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/24 15:45
     */
    @ApiOperation("获取小程序首页轮播图")
    @GetMapping("/get_index_swiper")
    public ResBean getIndexSwiper() {

        int a = 1/0;
        return ResBean.ok("ok", rollBookService.getAllIndexSwiper());
    }


}

package com.fall.robok.controller;

import com.fall.robok.service.impl.RollBookServiceImpl;
import com.fall.robok.util.bean.ResBean;
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
     * @author FAll
     * @description 获取首页轮播图
     * @param
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/24 15:45
     */
    @GetMapping("/get_index_swiper")
    public ResBean getIndexSwiper() {
        return ResBean.ok("ok", rollBookService.getAllIndexSwiper());
    }




}

package com.fall.robok.service;

import com.fall.robok.model.Img;

import java.util.ArrayList;

/**
 * @author FAll
 * @date 2022/11/21 10:28
 */
public interface IRollBookService {

    /**
     * @author FAll
     * @description 获取首页轮播图
     * @return: java.util.ArrayList<java.lang.String>
     * @date 2022/11/21 10:29
     */
    ArrayList<Img> getAllIndexSwiper();
}

package com.fall.robok.service.impl;

import com.fall.robok.mapper.IndexImgMapper;
import com.fall.robok.model.Img;
import com.fall.robok.service.IRollBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author FAll
 * @date 2022/9/24 14:36
 */

@Service
public class RollBookServiceImpl implements IRollBookService {

    private final IndexImgMapper indexImgMapper;

    @Autowired
    public RollBookServiceImpl(IndexImgMapper indexImgMapper){
        this.indexImgMapper = indexImgMapper;
    }

    /**
     * @param
     * @author FAll
     * @description 获取首页轮播图
     * @return: java.util.ArrayList<java.lang.String>
     * @date 2022/9/24 15:45
     */
    @Override
    public ArrayList<Img> getAllIndexSwiper() {
        return new ArrayList<>(indexImgMapper.getAllIndexSwiper());
    }

}

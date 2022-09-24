package com.fall.robok;

import com.alibaba.fastjson.JSON;
import com.fall.robok.mapper.IndexImgMapper;
import com.fall.robok.model.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author FAll
 * @date 2022/9/24 14:36
 */

@Service
public class RollBookService {

    @Autowired
    IndexImgMapper indexImgMapper;

    /**
     * @author FAll
     * @description 获取所有首页轮播图
     * @param
     * @return: java.util.ArrayList<java.lang.String>
     * @date 2022/9/24 15:45
     */
    public ArrayList<String> getAllIndexSwiper() {
        ArrayList<String> ret = new ArrayList<>();
        for (Img img : indexImgMapper.getAllIndexSwiper()) {
            ret.add(JSON.toJSONString(img));
        }
        return ret;
    }

}

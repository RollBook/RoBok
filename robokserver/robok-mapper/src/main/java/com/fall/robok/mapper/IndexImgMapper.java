package com.fall.robok.mapper;

import com.fall.robok.model.Img;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FAll
 * @date 2022/9/24 15:17
 */

@Mapper
@Repository
public interface IndexImgMapper {

    /**
     * @author FAll
     * @description 获取主页所有轮播图
     * @param
     * @return: java.util.List<com.fall.robok.model.Img>
     * @date 2022/9/24 15:20
     */
    List<Img> getAllIndexSwiper();

}

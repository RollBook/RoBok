package com.fall.robok.mapper;

import com.fall.robok.model.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
     * @return: java.util.List<com.fall.robok.model.Img>
     * @date 2022/9/24 15:20
     */
    @Select("SELECT `index_swiper0`, `index_swiper1`," +
            " `index_swiper2`, `index_swiper3`, " +
            "`index_swiper4`, `index_swiper5`, `index_swiper6` FROM `t_img_index`")
    Img getAllIndexSwiper();

}

package com.fall.robok.mapper;

import com.fall.robok.vo.SellerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author FAll
 * @date 2023/3/18 16:45
 */
@Mapper
@Repository
public interface SellerMapper {

    /**
     * @author FAll
     * @description 根据openid查询卖家信息
     * @param openid openid
     * @return: com.fall.robok.vo.SellerInfo
     * @date 2023/3/18 16:47
     */
    SellerInfo getSellerInfoByOpenId(String openid);
}

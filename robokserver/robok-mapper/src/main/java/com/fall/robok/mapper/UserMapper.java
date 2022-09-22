package com.fall.robok.mapper;

import com.fall.robok.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author FAll
 * @date 2022/9/22 14:05
 */
@Repository
public interface UserMapper {

    User getAllUser();

}

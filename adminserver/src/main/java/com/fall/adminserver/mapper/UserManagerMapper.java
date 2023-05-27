package com.fall.adminserver.mapper;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/5/8,
 * @time 12:38,
 */

@Mapper
@Repository
public interface UserManagerMapper {

    List<User> getUserList(String nickName, String order, String orderProp);

    Integer delUser(String openId);

    Integer updateUsr(User user);
}

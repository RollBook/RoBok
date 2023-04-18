package com.fall.adminserver.mapper;

import com.fall.adminserver.model.MenuSubItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FAll
 * @date 2023/4/16 下午12:03
 * 系统用户mapper
 */
@Mapper
@Repository
public interface SysUserMapper {

    @Select("""
            select * from t_admin_menu_sub_item;
            """)
    List<MenuSubItem> getSubItems();

}

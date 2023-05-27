package com.fall.adminserver.mapper;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FAll
 * @date 2023/4/13 下午4:06
 * 管理所有admin账户
 */
@Mapper
@Repository
public interface SysAdminManagerMapper {

    int register(SysUser sysUser);

    @Select("""
                select * from `t_admin`
                    where `name` = #{name}
            """)
    SysUser getSysUserByName(@Param("name") String name);

    List<SysUser> getAdmin(String name);

    Integer updateServiceAdmin(SysUser sysUser);

    Integer delServiceAdmin(String id);
}

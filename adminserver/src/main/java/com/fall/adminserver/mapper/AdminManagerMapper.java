package com.fall.adminserver.mapper;

import com.fall.adminserver.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author FAll
 * @date 2023/4/13 下午4:06
 * 管理所有admin账户
 */
@Mapper
@Repository
public interface AdminManagerMapper {

    int register(Admin admin);

    @Select("""
                select * from `t_admin`
                    where `name` = #{name}
            """)
    Admin getAdminByName(@Param("name") String name);

}

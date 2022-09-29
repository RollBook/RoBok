package com.fall.robok.mapper;

import com.fall.robok.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author FAll
 * @date 2022/9/26 22:28
 */

@Mapper
@Repository
public interface BookMapper {

    /**
     * @param book
     * @author FAll
     * @description 添加书本
     * @return: java.lang.Integer
     * @date 2022/9/26 22:36
     */
    Integer addBook(Book book);

    /**
     * @param book
     * @author FAll
     * @description 修改书本信息
     * @return: java.lang.Integer
     * @date 2022/9/29 20:11
     */
    Integer updateBook(Book book);

}

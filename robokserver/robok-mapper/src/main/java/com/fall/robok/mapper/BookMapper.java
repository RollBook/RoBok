package com.fall.robok.mapper;

import com.fall.robok.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FAll
 * @date 2022/9/26 22:28
 */

@Mapper
@Repository
public interface BookMapper {

    /**
     * @param book 书本
     * @author FAll
     * @description 添加书本
     * @return: java.lang.Integer
     * @date 2022/9/26 22:36
     */
    Integer addBook(Book book);

    /**
     * @param book 书本
     * @author FAll
     * @description 修改书本信息
     * @return: java.lang.Integer
     * @date 2022/9/29 20:11
     */
    Integer updateBook(Book book);


    /**
     * @author Tan
     * @description
     * @param openid openid
     * @return: java.util.List<com.fall.robok.model.Book>
     * @date  15:27
     */
    List<Book> getSellBook(@Param("openid") String openid);

}

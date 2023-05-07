package com.fall.robok.mapper;

import com.fall.robok.po.Book;
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


    Integer delBook(@Param("bookId") String bookId);


    Integer updateBookAudit(@Param("audit")Integer audit,@Param("bookId")String bookId);

    List<Book> getSaveBook(@Param("openid")String openid);

    Integer saveBook(@Param("bookId") String bookId);


}

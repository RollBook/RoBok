package com.fall.adminserver.mapper;

import com.fall.adminserver.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FAll
 * @date 2023/4/20 下午3:25
 * 书本管理mapper
 */
@Mapper
@Repository
public interface BookManagerMapper {

    List<Book> getBookList(String bookName,String order, String orderProp);

    Integer passAudit(@Param("bookId") String bookId);

    Integer noPassAudit(@Param("bookId") String bookId);

    Integer recycleAudit(@Param("bookId") String bookId);

    Integer updateBook(Book book);

    Integer delBook(String bookId);


}

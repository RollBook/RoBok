package com.fall.adminserver.service;

import com.fall.adminserver.model.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author FAll
 * @date 2023/4/20 下午3:17
 * 书本管理service
 */
public interface BookManagerService {
    PageInfo<Book> getBookList(Integer pageNum, Integer pageSize, String order, String orderProp);

    PageInfo<Book> getBookByName(String bookName,Integer pageNum, Integer pageSize, String order, String orderProp);

    PageInfo<Book> getAuditBookList(Integer pageNum, Integer pageSize, String order, String orderProp);

    PageInfo<Book> getAuditBookByName(String bookName,Integer pageNum, Integer pageSize, String order, String orderProp);

    Integer passAudit(String bookId);

    Integer noPassAudit(String bookId);

    PageInfo<Book> getRecycleAuditBookList(Integer pageNum, Integer pageSize, String order, String orderProp);

    PageInfo<Book> getRecycleAuditBookByName(String bookName,Integer pageNum, Integer pageSize, String order, String orderProp);

    Integer recycleAudit(String bookId);

}

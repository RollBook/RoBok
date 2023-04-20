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
    PageInfo<Book> getBookList(Integer pageNum, Integer pageSize);
}

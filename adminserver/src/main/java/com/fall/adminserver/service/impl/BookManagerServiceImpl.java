package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.BookManagerMapper;
import com.fall.adminserver.model.Book;
import com.fall.adminserver.service.BookManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FAll
 * @date 2023/4/20 下午3:19
 * 书本管理serviceImpl
 */
@Service
public class BookManagerServiceImpl implements BookManagerService {

    private final BookManagerMapper bookManagerMapper;

    public BookManagerServiceImpl(BookManagerMapper bookManagerMapper) {
        this.bookManagerMapper = bookManagerMapper;
    }

    @Override
    public PageInfo<Book> getBookList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Book> bookList = bookManagerMapper.getBookList();
        return new PageInfo<>(bookList,5);
    }
}

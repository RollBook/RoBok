package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.BookManagerMapper;
import com.fall.adminserver.model.Book;
import com.fall.adminserver.service.BookManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public PageInfo<Book> getBookList(Integer pageNum, Integer pageSize, String order, String orderProp) {

        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Book> bookList = bookManagerMapper.getBookList(null, order, orderProp);
        return new PageInfo<>(bookList,5);
    }

    @Override
    public PageInfo<Book> getBookByName(String bookName, Integer pageNum,
                                        Integer pageSize, String order, String orderProp) {

        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Book> bookList = bookManagerMapper.getBookList('%'+bookName+'%', order, orderProp);
        return new PageInfo<>(bookList,5);
    }

    @Override
    public PageInfo<Book> getAuditBookList(Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Book> bookList = bookManagerMapper.getBookList(null, order, orderProp);
        List<Book> toRemove = new ArrayList<>();
        for(Book book : bookList) {
            if(book.getAudit() != 0) {
                toRemove.add(book);
            }
        }
        bookList.removeAll(toRemove);
        return new PageInfo<>(bookList,5);
    }

    @Override
    public PageInfo<Book> getAuditBookByName(String bookName, Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Book> bookList = bookManagerMapper.getBookList('%'+bookName+'%', order, orderProp);
        List<Book> toRemove = new ArrayList<>();
        for(Book book : bookList) {
            if(book.getAudit() != 0) {
                toRemove.add(book);
            }
        }
        bookList.removeAll(toRemove);
        return new PageInfo<>(bookList,5);
    }

    @Override
    public Integer passAudit(String bookId) {
        return bookManagerMapper.passAudit(bookId);
    }

    @Override
    public Integer noPassAudit(String bookId) {
        return bookManagerMapper.noPassAudit(bookId);
    }

    @Override
    public PageInfo<Book> getRecycleAuditBookList(Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Book> bookList = bookManagerMapper.getBookList(null, order, orderProp);
        List<Book> toRemove = new ArrayList<>();
        for(Book book : bookList) {
            if(book.getAudit() != 9) {
                toRemove.add(book);
            }
        }
        bookList.removeAll(toRemove);
        return new PageInfo<>(bookList,5);
    }

    @Override
    public PageInfo<Book> getRecycleAuditBookByName(String bookName, Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Book> bookList = bookManagerMapper.getBookList('%'+bookName+'%', order, orderProp);
        List<Book> toRemove = new ArrayList<>();
        for(Book book : bookList) {
            if(book.getAudit() != 9) {
                toRemove.add(book);
            }
        }
        bookList.removeAll(toRemove);
        return new PageInfo<>(bookList,5);
    }

    @Override
    public Integer recycleAudit(String bookId) {
        return bookManagerMapper.recycleAudit(bookId);
    }

    @Override
    public Integer updateBook(Book book) {
        return bookManagerMapper.updateBook(book);
    }

    @Override
    public Integer delBook(String bookId) {
        return bookManagerMapper.delBook(bookId);
    }
}

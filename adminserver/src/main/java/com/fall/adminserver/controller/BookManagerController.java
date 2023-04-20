package com.fall.adminserver.controller;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.BookManagerService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FAll
 * @date 2023/4/13 下午2:42
 * 管理所有书本
 */
@RestController
@RequestMapping("/book")
public class BookManagerController {

    BookManagerService bookManagerService;

    public BookManagerController(BookManagerService bookManagerService) {
        this.bookManagerService = bookManagerService;
    }

    @Operation(summary = "获取书本列表")
    @GetMapping("/get_book_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Book>> getBookList(@RequestParam("pageNum")Integer pageNum,
                                                      @RequestParam("pageSize")Integer pageSize){

        return ResponseRecord.success(bookManagerService.getBookList(pageNum,pageSize));
    }
}

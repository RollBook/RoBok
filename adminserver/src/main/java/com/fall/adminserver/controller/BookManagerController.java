package com.fall.adminserver.controller;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.User;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.BookManagerService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
                                                      @RequestParam("pageSize")Integer pageSize,
                                                      @Nullable @RequestParam("order")String order,
                                                      @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord.success(bookManagerService.getBookList(pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "根据名称获取书本")
    @GetMapping("/get_book_by_name")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Book>> getBookByName(@RequestParam("bookName")String bookName,
                                                        @RequestParam("pageNum")Integer pageNum,
                                                        @RequestParam("pageSize")Integer pageSize,
                                                        @Nullable @RequestParam("order")String order,
                                                        @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord
                .success(bookManagerService.getBookByName(bookName,pageNum,pageSize,order,orderProp));
    }


    @Operation(summary = "获取待审核书本列表")
    @GetMapping("/get_audit_book_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Book>> getAuditBookList(@RequestParam("pageNum")Integer pageNum,
                                                      @RequestParam("pageSize")Integer pageSize,
                                                      @Nullable @RequestParam("order")String order,
                                                      @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord.success(bookManagerService.getAuditBookList(pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "根据名称获取待审核书本")
    @GetMapping("/get_audit_book_by_name")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Book>> getAuditBookByName(@RequestParam("bookName")String bookName,
                                                        @RequestParam("pageNum")Integer pageNum,
                                                        @RequestParam("pageSize")Integer pageSize,
                                                        @Nullable @RequestParam("order")String order,
                                                        @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord
                .success(bookManagerService.getAuditBookByName(bookName,pageNum,pageSize,order,orderProp));
    }


    @Operation(summary = "审核通过书本")
    @PostMapping("/pass_audit")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> passAudit(@RequestParam("bookId") String bookId){
        return Optional.ofNullable(bookManagerService.passAudit(bookId))
                .map(e->(ResponseRecord.success("审核通过",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "审核退回书本")
    @PostMapping("/no_pass_audit")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> noPassAudit(@RequestParam("bookId") String bookId){
        return Optional.ofNullable(bookManagerService.noPassAudit(bookId))
                .map(e->(ResponseRecord.success("退回通过",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "获取书本回收站书本列表")
    @GetMapping("/get_recycle_audit_book_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Book>> getRecycleAuditBookList(@RequestParam("pageNum")Integer pageNum,
                                                           @RequestParam("pageSize")Integer pageSize,
                                                           @Nullable @RequestParam("order")String order,
                                                           @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord.success(bookManagerService.getRecycleAuditBookList(pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "根据名称获取书本回收站书本")
    @GetMapping("/get_recycle_audit_book_by_name")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Book>> getRecycleAuditBookByName(@RequestParam("bookName")String bookName,
                                                             @RequestParam("pageNum")Integer pageNum,
                                                             @RequestParam("pageSize")Integer pageSize,
                                                             @Nullable @RequestParam("order")String order,
                                                             @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord
                .success(bookManagerService.getRecycleAuditBookByName(bookName,pageNum,pageSize,order,orderProp));
    }


    @Operation(summary = "回收书本")
    @PostMapping("/recycle_audit")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> recycleAudit(@RequestParam("bookId") String bookId){
        return Optional.ofNullable(bookManagerService.recycleAudit(bookId))
                .map(e->(ResponseRecord.success("回收成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "修改书本")
    @PostMapping("/update_book")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> updateBook(@RequestBody Book book){
        return Optional.ofNullable(bookManagerService.updateBook(book))
                .map(e->(ResponseRecord.success("修改成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "删除书本")
    @PostMapping("/del_book")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> delUser(@RequestParam String bookId){
        return Optional.ofNullable(bookManagerService.delBook(bookId))
                .map(e->(ResponseRecord.success("删除成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }
}

package com.fall.adminserver.controller;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.Order;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.OrderService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author FAll
 * @date 2023/4/13 下午2:42
 * 管理订单
 */
@RestController
@RequestMapping("/order")
public class OrderManagerController {

    private final OrderService orderService;

    public OrderManagerController(OrderService orderService){
        this.orderService = orderService;
    }

    @Operation(summary = "获取订单列表")
    @GetMapping("/get_order_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Order>> getOrderList(@RequestParam("pageNum")Integer pageNum,
                                                       @RequestParam("pageSize")Integer pageSize,
                                                       @Nullable @RequestParam("order")String order,
                                                       @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord.success(orderService.getOrder(pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "根据名称获取订单")
    @GetMapping("/get_order_by_name")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Order>> getOrderByName(@RequestParam("bookName")String bookName,
                                                        @RequestParam("pageNum")Integer pageNum,
                                                        @RequestParam("pageSize")Integer pageSize,
                                                        @Nullable @RequestParam("order")String order,
                                                        @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord
                .success(orderService.getOrderByName(bookName,pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "删除订单")
    @PostMapping("/del_order")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> delOrder(@RequestParam("orderId") String orderId){
        return Optional.ofNullable(orderService.delOrder(orderId))
                .map(e->(ResponseRecord.success("删除成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "获取回收站订单列表")
    @GetMapping("/get_recycle_order_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Order>> getRecycleOrderList(@RequestParam("pageNum")Integer pageNum,
                                                        @RequestParam("pageSize")Integer pageSize,
                                                        @Nullable @RequestParam("order")String order,
                                                        @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord.success(orderService.getRecycleOrder(pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "根据名称获取订单")
    @GetMapping("/get_recycle_order_by_name")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Order>> getRecycleOrderByName(@RequestParam("bookName")String bookName,
                                                          @RequestParam("pageNum")Integer pageNum,
                                                          @RequestParam("pageSize")Integer pageSize,
                                                          @Nullable @RequestParam("order")String order,
                                                          @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord
                .success(orderService.getRecycleOrderByName(bookName,pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "回收订单")
    @PostMapping("/recycle_order")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> recycleOrder(@RequestParam("orderId") String orderId){
        return Optional.ofNullable(orderService.recycleOrder(orderId))
                .map(e->(ResponseRecord.success("回收成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

}

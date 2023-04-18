package com.fall.adminserver.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author FAll
 * @date 2023/4/18 下午9:30
 * 菜单ID枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum MenuIDEnum {
    /* 用户管理 */ USER_MANAGE(1),
    USER_LIST(11), USER_FEEDBACK(12),
    CUSTOMER_SERVICE_LIST(13), CUSTOMER_INFORM(14), GENERAL_USER_MANAGE(15),


    /* 书本管理 */ BOOK_MANAGE(2),
    BOOK_LIST(21), BOOK_CHECK(22),
    BOOK_RECYCLE_BIN(23), GENERAL_BOOK_MANAGE(24),


    /* 订单管理 */ ORDER_MANAGE(3),
    ORDER_LIST(31), ORDER_RECYCLE_BIN(32), GENERAL_ORDER_MANAGE(33),


    /* 机器管理 */ MACHINE_MANAGE(4),
    MACHINE_LIST(41), GENERAL_MACHINE_MANAGE(42),


    /* 活动公告 */ ANNOUNCEMENT(5),
    ANNOUNCEMENT_LIST(51),EVENT_LIST(52);


    private final int id;
}

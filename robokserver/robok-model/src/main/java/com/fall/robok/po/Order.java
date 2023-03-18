package com.fall.robok.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FAll
 * @date 2022/9/29 21:17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    String orderId;

    String createdTime;

    String buyerId;

    String sellerId;

    String bookId;

    Book book;

}

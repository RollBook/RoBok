package com.fall.adminserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FAll
 * @date 2022/9/29 21:17
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    String orderId;

    String createdTime;

    String buyerId;

    String sellerId;

    Integer audit;

    String bookId;

    Book book;

}

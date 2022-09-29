package com.fall.robok.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author FAll
 * @date 2022/9/29 21:17
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    String orderId;

    String createdTime;

    String buyerId;

    String sellerId;

    String bookId;

}

package com.fall.robok.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 楠檀,
 * @date 2023/4/22,
 * @time 14:26,
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderOfPay {

    Integer price;

    String openid;

    String sellerId;

    String bookId;
}

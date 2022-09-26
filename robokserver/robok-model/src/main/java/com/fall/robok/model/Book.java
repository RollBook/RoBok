package com.fall.robok.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author FAll
 * @date 2022/9/24 21:35
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    String bookId;

    String openId;

    String bookName;

    Double price;

    Integer pressId;

    String status;

    Integer audit;

    Integer tagId;

    String url1;

    String url2;

    String url3;

    String description;

    String timestamp;

}

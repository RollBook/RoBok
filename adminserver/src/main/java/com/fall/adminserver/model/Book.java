package com.fall.adminserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FAll
 * @date 2023/4/20 下午3:27
 * 书本实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String bookId;

    private String openId;

    private String bookName;

    private Double price;

    private Integer pressId;

    private Integer status;

    private Integer audit;

    private Integer tagId;

    private String url1;

    private String url2;

    private String url3;

    private String description;

    private String timestamp;
}

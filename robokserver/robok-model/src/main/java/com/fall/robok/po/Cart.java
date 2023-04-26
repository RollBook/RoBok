package com.fall.robok.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 楠檀,
 * @date 2023/4/18,
 * @time 17:25,
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private String bookId;

    private String sellId;

    private String bookName;

    private Double price;

    private String url1;

    private String description;

    private String userId;

    private Boolean checked;
}

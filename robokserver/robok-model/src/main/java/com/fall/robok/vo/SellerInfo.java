package com.fall.robok.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FAll
 * @date 2023/3/18 15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfo {
    private String nickName;

    private String school;

    private String phone;

    private String address;

    private Double latitude;

    private Double longitude;
}

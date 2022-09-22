package com.fall.robok.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author FAll
 * @date 2022/9/22 13:59
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int openId;

    private String nickName;

    private String createdTime;

    private String phone;

    private String email;

}

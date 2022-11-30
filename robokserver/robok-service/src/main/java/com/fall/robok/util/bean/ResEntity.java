package com.fall.robok.util.bean;

import org.springframework.http.HttpStatus;

/**
 * @author FAll
 * @date 2022/11/30 7:38
 */
public class ResEntity<T> {

    private HttpStatus status;

    private Integer code;

    private T data;


}

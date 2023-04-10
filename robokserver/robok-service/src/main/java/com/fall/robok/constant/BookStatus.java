package com.fall.robok.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author FAll
 * @date 2023/3/23 20:10
 */
@Getter
@ToString
@AllArgsConstructor
public enum BookStatus {

    COMPLETE(8)  ,  // 无缺页、残页
    CLEAR_PRINT(4),  // 印刷清晰
    NO_PAINTING(2),  // 无污渍、笔记
    CLEAR_COVER(1);  // 封面干净

    private final int status;
}

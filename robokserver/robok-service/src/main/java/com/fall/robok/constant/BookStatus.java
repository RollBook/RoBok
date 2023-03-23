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
    CLEARPRINT(4),  // 印刷清晰
    NOPAINTING(2),  // 无污渍、笔记
    CLEARCOVER(1);  // 封面干净

    private final int status;
}

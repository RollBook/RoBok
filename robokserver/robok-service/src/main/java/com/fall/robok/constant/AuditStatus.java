package com.fall.robok.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author FAll
 * @date 2023/3/23 20:06
 */
@Getter
@ToString
@AllArgsConstructor
public enum AuditStatus {

    UNCHECKED(0),CHECKED(1);

    private final int status;
}

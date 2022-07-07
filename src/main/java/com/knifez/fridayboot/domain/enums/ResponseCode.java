package com.knifez.fridayboot.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhang
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS(0,"操作成功"),
    /**
     * 失败
     */
    ERROR(1,"操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 消息
     */
    private final String message;

}

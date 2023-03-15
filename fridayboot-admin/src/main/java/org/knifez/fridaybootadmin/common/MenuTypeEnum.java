package org.knifez.fridaybootadmin.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author KnifeZ
 * @date 2023/02/10
 */
@Getter
public enum MenuTypeEnum implements IEnum<Integer> {
    /**
     * 目录
     */
    FOLDER(0, "目录"),
    /**
     * 菜单
     */
    MENU(1, "菜单"),
    /**
     * 按钮
     */
    BUTTON(2, "按钮");

    @EnumValue
    private final Integer value;

    private final String text;

    MenuTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }
}

package org.knifez.fridaybootadmin.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;
import org.knifez.fridaybootcore.dto.TextValuePair;

import java.util.ArrayList;
import java.util.List;

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
    MENU_FOLDER(0, "目录"),
    /**
     * 菜单
     */
    MENU(1, "菜单"),
    /**
     * 按钮
     */
    MENU_BUTTON(2, "按钮");

    @EnumValue
    private final Integer value;

    private final String text;

    MenuTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static List<TextValuePair> toList() {
        List<TextValuePair> list = new ArrayList<>();
        for (MenuTypeEnum item : MenuTypeEnum.values()) {
            list.add(new TextValuePair(item.getText(), item.getValue()));
        }
        return list;
    }
}

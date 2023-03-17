package org.knifez.fridaybootcore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextValuePair {
    private String text;
    private Object value;

    public TextValuePair(String text, Object value) {
        this.text = text;
        this.value = value;
    }

    public static TextValuePair from(String text, Object value) {
        return new TextValuePair(text, value);
    }
}

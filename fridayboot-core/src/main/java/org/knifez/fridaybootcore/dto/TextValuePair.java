package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "TextValuePair")
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

package org.knifez.fridaybootcore.exception;

import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.enums.ResultStatus;

/**
@author KnifeZ
 */
@Getter
@Setter
public class FridayResultException extends RuntimeException {

    /**
     * 结果状态
     */
    private final ResultStatus resultStatus;


    public FridayResultException(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }
}

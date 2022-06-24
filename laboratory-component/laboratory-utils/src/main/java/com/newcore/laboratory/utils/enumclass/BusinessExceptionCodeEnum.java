package com.newcore.laboratory.utils.enumclass;

import com.newcore.laboratory.utils.exception.BusinessException;

import java.util.Arrays;
import java.util.Optional;

/**
 * 业务异常错误码统一处理枚举类
 * @author zhouchaowei
 */
public enum BusinessExceptionCodeEnum {

    SUCCESS("00000","success"),
    FAIL("99999","fail"),

    F0322("F0322","文件未找到"),
    F0323("F0323","文件下载出错");

    /** 异常状态码 */
    private final String code;

    /** 异常提示信息 */
    private final String message;

    /**
     * 异常处理构造方法
     * @param code 异常状态码
     * @param message 异常提示信息
     */
    BusinessExceptionCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    /**
     * 查找对应的Key值
     * @param key 键
     * @return 值
     */
    public static BusinessExceptionCodeEnum valueOfKey(String key) {
        Optional<BusinessExceptionCodeEnum> optionalValue = Arrays.stream(values()).filter((item) -> {
            return item.getCode().equals(key);
        }).findFirst();
        if (optionalValue.isPresent()) {
            return optionalValue.get();
        } else {
            throw new BusinessException("Can't find enum BusinessExceptionCodeEnum for key " + key);
        }
    }
}

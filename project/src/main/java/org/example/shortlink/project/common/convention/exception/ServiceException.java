package org.example.shortlink.project.common.convention.exception;

import org.example.shortlink.project.common.convention.errorcode.BaseErrorCode;
import org.example.shortlink.project.common.convention.errorcode.IErrorCode;

import java.util.Optional;

/**
 * 服务端异常
 */
public class ServiceException extends AbstractException {
    // 只传 IErrorCode（错误编码）
    public ServiceException(IErrorCode errorCode) { this(null, errorCode);}
    // 只传 String message（自定义消息）
    public ServiceException(String message) { this(message, null, BaseErrorCode.SERVICE_ERROR);}
    // 传 String message + IErrorCode（自定义消息 + 错误编码）
    public ServiceException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }
    // 传 String message + Throwable + IErrorCode（全参数）
    public ServiceException(String message, Throwable throwable, IErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message()), throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}


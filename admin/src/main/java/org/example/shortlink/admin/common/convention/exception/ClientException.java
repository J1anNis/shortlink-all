package org.example.shortlink.admin.common.convention.exception;

import org.example.shortlink.admin.common.convention.errorcode.BaseErrorCode;
import org.example.shortlink.admin.common.convention.errorcode.IErrorCode;

/**
 * 客户端异常
 */
public class ClientException extends AbstractException {

    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    } // 只传 “错误码”，不传自定义消息和根源异常。
    // 比如：客户端传的 “用户 ID 格式错误”，此时不需要额外写消息
    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}

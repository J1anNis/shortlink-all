package org.example.shortlink.admin.common.convention.exception;

import org.example.shortlink.admin.common.convention.errorcode.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 抽象项目中三类异常体系，客户端异常、服务端异常以及远程服务调用异常
 *
 * @see ClientException
 * @see ServiceException
 * @see RemoteException
 */
@Getter
public abstract class AbstractException extends RuntimeException {

    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
/**
 * 先通过 StringUtils.hasLength(message) 判断 “传入的自定义 message 是否有效”（非 null、非空字符串、非纯空格）；
 * 若有效（如传入 message = "用户 ID=123 不存在"），则 errorMessage 用这个自定义信息；
 * 若无效（如 message = null 或 message = " "），则通过 orElse(errorCode.message()) 取 IErrorCode 的默认描述（如 IErrorCode 是 USER_NOT_FOUND，其 message() 返回 “用户不存在”）。
 * 为什么用 Optional？
 * 避免直接使用 if-else 判断 null，让空值处理更优雅，同时确保 errorMessage 最终一定有值（不会为 null），前端展示时无需额外判断空。
 *
 * 第二点
 * Throwable是一个用于溯源根本问题的类，顶级父类
 * 能够记录异常的根本问题，以及根本原因
 * 如果没有的话，只能看到被包装的问题，无法找到根本原因
 */
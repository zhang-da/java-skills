package com.da.learn.template.thymeleaf.exception;

import com.da.learn.template.thymeleaf.result.Result;
import com.da.learn.template.thymeleaf.result.ResultConstants;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AirException.class)
    public Result<Object> handlerAirException(AirException e) {
        return Result.failed(e.getCode(), e.getMsg());
    }

    /**
     * 拦截访问权限异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result authorizationException(AuthorizationException e, HttpServletRequest request,
                                         HttpServletResponse response) {
        int code = 401;
        String msg = "权限不足";

        // 获取异常信息
        Throwable cause = e.getCause();
        String message = cause.getMessage();
        Class<Result> resultClass = Result.class;

        // 判断无权限访问的方法返回对象是否为Result
        if (!message.contains(resultClass.getName())) {
            try {
                // 重定向到无权限页面
                String contextPath = request.getContextPath();
                // 在shiroConfig中也定义了/noAuth，抽离变量
                response.sendRedirect(contextPath + "/noAuth");
            } catch (IOException e1) {
                return Result.failed(code, msg);
            }
        }
        return Result.failed(code, msg);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Object> handlerNoFoundException(Exception e) {
        return Result.failed(ResultConstants.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }


    /**
     * servlet参数异常处理
     *
     * @param ex
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return Result.failed("参数异常:" + ex.getMessage());
    }

    /**
     * @param ex
     * @Valid校验异常处理 提交 json 请求体校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult exceptions = ex.getBindingResult();
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                return Result.failed("参数校验异常:" + fieldError.getDefaultMessage());
            }
        }
        return Result.failed("参数校验异常");
    }

    /**
     * get or post 表单单个参数校验错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> handleValidation(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            sb.append(violation.getMessage());
        }
        return Result.failed(sb.toString());
    }


    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        return Result.failed(e.getMessage());
    }
}

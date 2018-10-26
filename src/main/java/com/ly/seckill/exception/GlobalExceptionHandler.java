package com.ly.seckill.exception;

import com.ly.seckill.result.CodeMsg;
import com.ly.seckill.result.SeckillResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SeckillResult exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof BindException) {
            List<ObjectError> allErrors = ((BindException) e).getAllErrors();
            allErrors.get(0);
            String error = allErrors.get(0).getDefaultMessage();
            return SeckillResult.error(CodeMsg.BIND_ERROR.fillArgs(error));
        }
        if (e instanceof GlobalException) {
            return SeckillResult.error(((GlobalException) e).getCodeMsg());
        }
        if (e instanceof SeckillException) {
            return SeckillResult.error(((SeckillException) e).getCodeMsg());
        }
        return SeckillResult.error(CodeMsg.SERVER_ERROR);
    }
}

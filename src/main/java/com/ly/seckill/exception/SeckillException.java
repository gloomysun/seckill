package com.ly.seckill.exception;

import com.ly.seckill.result.CodeMsg;

public class SeckillException extends RuntimeException{
    private CodeMsg codeMsg;

    public SeckillException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

}

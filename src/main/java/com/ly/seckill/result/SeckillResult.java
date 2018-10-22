package com.ly.seckill.result;

public class SeckillResult<T> {
    private int code;
    private String msg;
    private T data;


    public static SeckillResult success() {
        return new SeckillResult(0, "success", null);
    }

    public static <T> SeckillResult success(T data) {
        return new SeckillResult(0, "success", data);
    }

    public static SeckillResult error(CodeMsg codeMsg) {
        return new SeckillResult(codeMsg);
    }

    private SeckillResult(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public SeckillResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SeckillResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

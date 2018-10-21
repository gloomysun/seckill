package com.ly.seckill.result;

public class Result<T> {
    private int code;
    private String msg;
    private T data;


    public static Result success() {
        return new Result(0, "success", null);
    }

    public static <T> Result success(T data) {
        return new Result(0, "success", data);
    }

    public static Result error(CodeMsg codeMsg) {
        return new Result(codeMsg);
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public Result(int code, String msg, T data) {
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
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

package spring.boot.rest.common;

/**
 * Created by Mtime on 2016/10/27.
 */
public class RestResult<T> {
    private String code;
    private String message;

    private T data;

    public RestResult() {
        this.setCode(ResultCode.SUCCESS);
    }
    public RestResult(T data) {
        this();
        this.setData(data);
    }

    public RestResult(ResultCode code) {
        this.setCode(code);
    }

    public RestResult(ResultCode code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public RestResult(ResultCode code, T data) {
        this(code);
        this.setData(data);
    }

    public RestResult(ResultCode code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code.val();
        this.message = code.msg();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

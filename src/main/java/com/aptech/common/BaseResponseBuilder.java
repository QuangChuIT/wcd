package com.aptech.common;


import java.io.IOException;
import java.util.Properties;

public class BaseResponseBuilder {
    public static final String CODE_OK = "WCD-00000000";
    public static final String CODE_NO_CONTENT = "WCD-00000204";
    public static final String CODE_INTERNAL_SERVER_ERROR = "WCD-00000500";

    private static final Properties props;

    static {
        props = new Properties();
        try {
            props.load(BaseResponseBuilder.class.getClassLoader().getResourceAsStream("error_codes.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> BaseResponse<T> of(T data, String request_id, String message) {
        if (data instanceof Iterable<?>) {
            if (!((Iterable) data).iterator().hasNext()) {
                return of(data, request_id, message, CODE_NO_CONTENT);
            } else {
                return of(data, request_id, message, CODE_OK);
            }
        } else {
            return of(data, request_id, message, CODE_INTERNAL_SERVER_ERROR);
        }
    }


    public static <T> BaseResponse<T> of(T data, String request_id, String message, String status) {
        BaseResponse<T> resp = new BaseResponse<>();
        resp.setErrorCode(status);
        resp.setRequestId(request_id);
        if (message == null) {
            resp.setMessage(props.getProperty(status, "unset"));
        } else {
            resp.setMessage(message);
        }
        resp.setData(data);
        return resp;
    }

}

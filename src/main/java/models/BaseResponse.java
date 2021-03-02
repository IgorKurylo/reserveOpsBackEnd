package models;

public class BaseResponse<E> {

    E data;
    String message;
    boolean isSuccess;

    public BaseResponse(E data, String message, boolean isSuccess) {
        this.data = data;
        this.message = message;
        this.isSuccess = isSuccess;
    }
}

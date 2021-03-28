package models;

public class BaseRequest<E> {
    private E data;

    public BaseRequest(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}

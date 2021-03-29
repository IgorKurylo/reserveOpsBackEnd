package interfaces;

public interface IConverter {
    Object Serializable(Object output);
    <E extends Object> Object Deserializable(String input,Class<E> eClass);
}

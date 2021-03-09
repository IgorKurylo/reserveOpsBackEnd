package utils;

import com.google.gson.Gson;

public class JsonConverter implements IConverter {
    Gson gson;

    public JsonConverter() {
        gson = new Gson();
    }

    @Override
    public Object Serializable(Object o) {
        return gson.toJson(o);
    }

    @Override
    public <E> Object Deserializable(String input, Class<E> eClass) {
        return gson.fromJson(input, eClass);
    }


}

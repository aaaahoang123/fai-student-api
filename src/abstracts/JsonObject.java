package abstracts;

import java.util.Map;

public abstract class JsonObject <T> {
    T data;
    Map<String, Object> meta;

    public JsonObject() {}

    public JsonObject(T data) {
        this.data = data;
    }

    public JsonObject(T data, Map<String, Object> meta) {
        this.data = data;
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }
}

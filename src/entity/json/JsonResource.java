package entity.json;

import com.googlecode.objectify.annotation.Id;

import java.lang.reflect.Field;
import java.util.HashMap;

public class JsonResource {
    private String type;
    private String id;
    private HashMap<String, Object> attributes;

    public JsonResource() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public static <T> JsonResource getInstance(T obj) {
        JsonResource data = new JsonResource();
        HashMap<String, Object> map = new HashMap<>();

        for (Field f:obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.isAnnotationPresent(Id.class)) {
                    data.setId(f.get(obj).toString());
                    continue;
                }
                map.put(f.getName(), f.get(obj));
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
        data.setType(obj.getClass().getSimpleName());
        data.setAttributes(map);
        return data;
    }

}

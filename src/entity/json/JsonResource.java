package entity.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

    /**
     *
     * @param T obj
     * @param <T>
     * @return get a JsonResource with an object
     */
    public static <T> JsonResource getExposeInstance(T obj) {
        JsonResource data = new JsonResource();

        for (Field f:obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.isAnnotationPresent(Id.class)) {
                    data.setId(f.get(obj).toString());
                    continue;
                }
                data.addAttribute(f.getName(), f.get(obj));
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
        data.setType(obj.getClass().getSimpleName());
        return data;
    }

    /**
     *
     * @param type
     * @param <T>
     * @return an instance from this JsonResource, with the class of object
     * If this resource has id, then put the id into attribute with field name annotate by @Id of objectify
     * And then stringify HashMap<String, Object> this.attribute to json string, and then parse it with the input type
     */
    public <T> T getInstance(Class<T> type) throws JsonSyntaxException {
        String idField = null;
        if (this.id != null && this.id.length() > 0) {
            for (Field f : type.getDeclaredFields()) {
                if (f.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)) {
                    idField = f.getName();
                    break;
                }
            }
        }
        if (idField!=null) addAttribute(idField, this.id);

        return new Gson().fromJson(getAttributeJsonString(), type);
    }

    public void addAttribute(String key, Object value) {
        if (this.attributes == null) this.attributes = new HashMap<>();
        this.attributes.put(key, value);
    }

    public String getAttributeJsonString() {
        return new Gson().toJson(this.attributes);
    }
}

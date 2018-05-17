package design_java_rest.entity;

import com.google.gson.JsonSyntaxException;
import design_java_rest.util.RESTJsonUtil;

import java.lang.reflect.Field;
import java.util.HashMap;

public class RESTResource {
    private String type;
    private String id;
    private HashMap<String, Object> attributes;

    public RESTResource() {
    }

    public RESTResource(String type, String id, HashMap<String, Object> attributes) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
    }

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

    public void addAttributes(String name, Object value){
        if(this.attributes == null){
            this.attributes = new HashMap<>();
        }
        this.attributes.put(name, value);
    }

    public String getAttributesString(){
        return RESTJsonUtil.GSON.toJson(this.attributes);
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * This function tranform from an Object class to RESTResource class. All of
     * class fields will be added to Resource attributes HashMap.
     * @param <T>
     *            type of object.
     *
     * @param object
     *            Object to transform.
     */

    public static <T> RESTResource getInstance(T object){
        RESTResource resource = new RESTResource();
        resource.setType(object.getClass().getSimpleName());
        try {
            for (Field field: object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)){
                    resource.setId(String.valueOf(field.get(object)));
                }else {
                    resource.addAttributes(field.getName(), field.get(object));
                }
            }
        }catch (IllegalArgumentException | IllegalAccessException e){

        }

        return resource;
    }

    /**
     *
     * This function tranform an existing RESTResource Object to Object class by type
     *
     * @param type
     *      Type of class will be return
     * @throws JsonSyntaxException
     */

    public <T> T getInstance(Class<T> type) throws JsonSyntaxException {
        return RESTJsonUtil.GSON.fromJson(this.getAttributesString(), type);
    }
}

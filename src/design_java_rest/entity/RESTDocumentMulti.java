package design_java_rest.entity;

import design_java_rest.util.RESTJsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RESTDocumentMulti implements RESTDocument {
    private ArrayList<RESTResource> data;
    private HashMap<String, Object> meta;

    public RESTDocumentMulti(){
        super();
    }

    /**
     * This function used to read all data from request input stream and cast to
     * RESTDocument as an array of RESTResource object.
     * */

    public static RESTDocumentMulti getInstanceFromRequest(HttpServletRequest req) throws IOException {
        return RESTJsonUtil.GSON.fromJson(RESTJsonUtil.parseStringInputStream(req.getInputStream()), RESTDocumentMulti.class);
    }

    public ArrayList<RESTResource> getData() {
        return data;
    }

    public void setData(ArrayList<RESTResource> data) {
        this.data = data;
    }

    public HashMap<String, Object> getMeta() {
        if(this.meta == null){
            this.meta = new HashMap<>();
        }
        return this.meta;
    }

    public void setMeta(HashMap<String, Object> meta) {
        this.meta = meta;
    }
    public void addMeta(String name, Object value){
        if(this.meta == null){
            this.meta = new HashMap<>();
        }
        this.meta.put(name, value);
    }

    protected RESTDocumentMulti(Builder builder){
        this.data = builder.data;
        this.meta = builder.meta;
    }

    public static class Builder{
        ArrayList<RESTResource> data;
        HashMap<String, Object> meta;

        public Builder setData(ArrayList<RESTResource> data) {
            this.data = data;
            return this;
        }

        public Builder setMeta(HashMap<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder addMeta(String name, Object value){
            if(this.meta == null){
                this.meta = new HashMap<>();
            }
            this.meta.put(name, value);
            return this;
        }

        public RESTDocumentMulti build(){
            return new RESTDocumentMulti(this);
        }
    }
}

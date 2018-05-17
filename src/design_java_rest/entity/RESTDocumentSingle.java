package design_java_rest.entity;

import design_java_rest.util.RESTJsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RESTDocumentSingle implements RESTDocument {
    private RESTResource data;
    private HashMap<String, Object> meta;
    private ArrayList<RESTError> errors;

    public RESTDocumentSingle(){
        super();
    }

    /**
     * This function used to read all data from request input stream and cast to
     * RESTDocument as a single RESTResource object.
     */

    public static RESTDocumentSingle getInstanceFromRequest(HttpServletRequest req)throws IOException {
        return RESTJsonUtil.GSON.fromJson(RESTJsonUtil.parseStringInputStream(req.getInputStream()), RESTDocumentSingle.class);
    }

    public RESTResource getData() {
        return data;
    }

    public void setData(RESTResource data) {
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

    public ArrayList<RESTError> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<RESTError> errors) {
        this.errors = errors;
    }

    protected RESTDocumentSingle(Builder builder){
        this.data = builder.data;
        this.meta = builder.meta;
        this.errors = builder.errors;
    }

    public static class Builder {
        RESTResource data;
        HashMap<String, Object> meta;
        ArrayList<RESTError> errors;

        public Builder setData(RESTResource data) {
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

        public Builder setErrors(ArrayList<RESTError> errors) {
            this.errors = errors;
            return this;
        }

        public Builder addError(RESTError error) {
            if (this.errors == null) {
                this.errors = new ArrayList<RESTError>();
            }
            this.errors.add(error);
            return this;
        }

        public RESTDocumentSingle build(){
            return new RESTDocumentSingle(this);
        }
    }
}

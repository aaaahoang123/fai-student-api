package entity.error;

import abstracts.RESTResponseDocument;

import java.util.ArrayList;
import java.util.List;

public class RESTError implements RESTResponseDocument {
    private List<ErrorResource> errors;

    public RESTError() {
    }

    public void addRs(ErrorResource resource){
        if(errors == null) errors = new ArrayList<>();
        errors.add(resource);
    }

    public RESTError(List<ErrorResource> errors) {
        this.errors = errors;
    }

    public List<ErrorResource> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResource> errors) {
        this.errors = errors;
    }
}
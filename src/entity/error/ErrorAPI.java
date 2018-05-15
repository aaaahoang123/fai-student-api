package entity.error;

import java.util.ArrayList;
import java.util.List;

public class ErrorAPI {
    private List<ErrorResource> errors;

    public ErrorAPI() {
    }

    public void addRs(ErrorResource resource){
        if(errors == null) errors = new ArrayList<>();
        errors.add(resource);
    }

    public ErrorAPI(List<ErrorResource> errors) {
        this.errors = errors;
    }

    public List<ErrorResource> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResource> errors) {
        this.errors = errors;
    }
}
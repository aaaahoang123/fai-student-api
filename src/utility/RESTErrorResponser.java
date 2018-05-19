package utility;

import abstracts.RESTGeneralError;
import abstracts.RESTResponser;
import entity.error.RESTError;
import entity.error.ErrorResource;

import java.util.ArrayList;
import java.util.List;

public class RESTErrorResponser extends RESTResponser{

    private List<ErrorResource> errors;
    private RESTGeneralError defautE;

    public RESTErrorResponser(RESTGeneralError define) {
        setCode(define.code());
        this.defautE = define;
    }

    private void initial() {
        if (this.errors == null) this.errors = new ArrayList<>();
    }

    @Override
    public void buildResponseDocument() {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
            addError(defautE);
        }
        setDocument(new RESTError(errors));
    }

    public RESTErrorResponser addError(ErrorResource e) {
        initial();
        this.errors.add(e);
        return this;
    }

    public RESTErrorResponser addError(String code, String title, String detail) {
        initial();
        this.errors.add(ErrorResource.getInstance(code,title,detail));
        return this;
    }

    public RESTErrorResponser addError(RESTGeneralError e) {
        initial();
        this.errors.add(ErrorResource.getInstance(String.valueOf(e.code()), e.description(), e.description()));
        return this;
    }

    public RESTErrorResponser addError(RESTGeneralError e, String description) {
        initial();
        this.errors.add(ErrorResource.getInstance(String.valueOf(e.code()), e.description(), description));
        return this;
    }

    public RESTErrorResponser addError(String message) {
        return addError(this.defautE, message);
    }

    public RESTErrorResponser setError(List<ErrorResource> le) {
        this.errors = le;
        return this;
    }

}

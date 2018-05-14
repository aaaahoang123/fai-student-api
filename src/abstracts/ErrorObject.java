package abstracts;

public abstract class ErrorObject <T> {
    private T errors;

    public ErrorObject() {
    }

    public ErrorObject(T errors) {
        this.errors = errors;
    }

    public T getErrors() {
        return errors;
    }

    public void setErrors(T errors) {
        this.errors = errors;
    }
}

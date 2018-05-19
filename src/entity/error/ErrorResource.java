package entity.error;

import java.util.ArrayList;
import java.util.List;

public class ErrorResource {
    private String code;
    private String title;
    private String detail;

    public ErrorResource() {}

    public static ErrorResource getInstance(String code, String title, String detail) {
        ErrorResource e = new ErrorResource();
        e.setCode(code);
        e.setTitle(title);
        e.setDetail(detail);
        return e;
    }

    private ErrorResource(Builder b) {
        this.code = b.code;
        this.title = b.title;
        this.detail = b.detail;
    }

    public static ErrorResource getInstance() {
        return new ErrorResource();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static class Builder {
        private String code;
        private String title;
        private String detail;

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDetail(String detail) {
            this.detail = detail;
            return this;
        }

        public ErrorResource build() {
            return new ErrorResource(this);
        }
    }

}

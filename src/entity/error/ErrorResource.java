package entity.error;

public class ErrorResource {
    private String code;
    private String title;
    private String detail;

    public static ErrorResource getInstance(String code, String title, String detail) {
        ErrorResource e = new ErrorResource();
        e.setCode(code);
        e.setTitle(title);
        e.setDetail(detail);
        return e;
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
}

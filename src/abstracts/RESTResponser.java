package abstracts;

import com.google.gson.Gson;
import entity.RESTConstantHttp;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class RESTResponser {
    private int code;
    private String contentType = RESTConstantHttp.HEADER_CONTENT_TYPE;
    private String encode = RESTConstantHttp.RESPONSE_ENCODING;
    private RESTResponseDocument document;

    public RESTResponser() {}

    public void setCode(int code) {
        this.code = code;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    void build(HttpServletResponse res) {
        res.setStatus(this.code);
    }

    public void doResponse(HttpServletResponse res) throws IOException {
        buildResponseDocument();
        build(res);
        res.getWriter().print(new Gson().toJson(this.document));
    }

    public abstract void buildResponseDocument();

    public RESTResponser setDocument(RESTResponseDocument document) {
        this.document = document;
        return this;
    }

}

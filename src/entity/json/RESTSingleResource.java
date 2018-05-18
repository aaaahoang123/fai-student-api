package entity.json;

import abstracts.JsonObject;
import abstracts.RESTResponseDocument;
import com.google.gson.Gson;
import utility.BodyParser;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RESTSingleResource extends JsonObject<JsonResource> {

    public static RESTSingleResource getInstanceFromRequest(HttpServletRequest req) {
        return new Gson().fromJson(BodyParser.parseBody(req), RESTSingleResource.class);
    }

    public RESTSingleResource() {
        super();
    }

    protected RESTSingleResource(Builder b) {
        this.setData(b.data);
        this.setMeta(b.meta);
    }

    public static class Builder {
        JsonResource data;
        Map<String, Object> meta;

        public Builder setData(JsonResource data) {
            this.data = data;
            return this;
        }

        public Builder setMeta(Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder addMeta(String key, Object value) {
            if (this.meta == null) this.meta = new HashMap<>();
            this.meta.put(key, value);
            return this;
        }

        public RESTSingleResource build() {
            return new RESTSingleResource(this);
        }
    }
}

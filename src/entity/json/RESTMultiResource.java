package entity.json;

import abstracts.JsonObject;
import abstracts.RESTResponseDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RESTMultiResource extends JsonObject<List<JsonResource>> {
    public RESTMultiResource() {
        super();
    }

    protected RESTMultiResource(Builder b) {
        this.setData(b.data);
        this.setMeta(b.meta);
    }

    public static class Builder {
        List<JsonResource> data;
        Map<String, Object> meta;

        public <T> Builder setData(List<T> data) {
            if (data.size() != 0) {
                if (data.get(0).getClass().getSimpleName().equals("JsonResource")) this.data = (List<JsonResource>) data;
                return this;
            }
            if (this.data == null) this.data = new ArrayList<>();
            for (T t: data) {
                this.data.add(JsonResource.getExposeInstance(t));
            }
            return this;
        }
        public Builder setMeta(Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder addData(JsonResource jr) {
            if (this.data == null) this.data = new ArrayList<>();
            this.data.add(jr);
            return this;
        }

        public Builder putMeta(String key, Object value) {
            if (meta == null) meta = new HashMap<>();
            if (value != null) meta.put(key, value);
            return this;
        }

        public RESTMultiResource build() {
            return new RESTMultiResource(this);
        }
    }
}

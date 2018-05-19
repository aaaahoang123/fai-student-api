package utility;

import abstracts.RESTGeneralSuccess;
import abstracts.RESTResponser;
import entity.json.JsonResource;
import entity.json.RESTMultiResource;
import entity.json.RESTSingleResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RESTSuccessResponser extends RESTResponser {

    private List<JsonResource> data;
    private Map<String, Object> meta;

    public RESTSuccessResponser(RESTGeneralSuccess define) {
        setCode(define.code());
    }

    @Override
    public void buildResponseDocument() {
        if (this.data != null && this.data.size() > 0) {
            if (this.data.size() == 1) {
                setDocument(new RESTSingleResource.Builder().setData(data.get(0)).build());
            }
            else {
                setDocument(new RESTMultiResource.Builder()
                        .setData(this.data)
                        .setMeta(this.meta)
                        .build());
            }
        }
        else setDocument(new RESTSingleResource());
    }


    public RESTSuccessResponser addData(JsonResource d) {
        if (this.data == null) this.data = new ArrayList<>();
        this.data.add(d);
        return this;
    }

    public <T> RESTSuccessResponser addData(T object) {
        if (this.data == null) this.data = new ArrayList<>();
        this.data.add(JsonResource.getExposeInstance(object));
        return this;
    }

    public <T> RESTSuccessResponser addData(List<T> data) {
        if (this.data == null) this.data = new ArrayList<>();
        for (T t: data) {
            this.data.add(JsonResource.getExposeInstance(t));
        }
        return this;
    }
    public RESTSuccessResponser setData(List<JsonResource> ld) {
        this.data = ld;
        return this;
    }

    public RESTSuccessResponser setMeta(Map<String, Object> meta) {
        this.meta = meta;
        return this;
    }

    public RESTSuccessResponser putMeta(String key, Object value) {
        if (this.meta == null) this.meta = new HashMap<>();
        this.meta.put(key, value);
        return this;
    }

}

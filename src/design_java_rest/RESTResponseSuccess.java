package design_java_rest;

import design_java_rest.entity.RESTDocumentMulti;
import design_java_rest.entity.RESTDocumentSingle;
import design_java_rest.entity.RESTResource;
import design_java_rest.util.RESTJsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class used to build response's content before sending to REST Api
 * client. Content is formatted following jsonapi format below.<br>
 * 
 * @see <a href="http://jsonapi.org/format/">http://jsonapi.org/format/</a>
 * 
 * @author xuanhung2401@gmail.com
 * 
 * @doc Sep 16, 2015 9:40:25 AM
 *
 */
public class RESTResponseSuccess extends RESTResponse {
	private ArrayList<RESTResource> data;
	private HashMap<String, Object> meta;

	public RESTResponseSuccess(RESTGeneralSuccess define) {
		this.code = define.code();
	}

	void buildResponseDocument() {
		if (this.data != null && this.data.size() > 0) {
			if (this.data.size() == 1) {
				this.document = new RESTDocumentSingle.Builder().setData(this.data.get(0)).build();
			} else {
				this.document = new RESTDocumentMulti.Builder().setData(this.data).setMeta(this.meta).build();
			}
		} else {
			this.document = new RESTDocumentSingle();
		}
	}

	@Override
	public String toJsonString() {
		buildResponseDocument();
		return RESTJsonUtil.GSON.toJson(this.document);
	}

	@Override
	public void doResponse(HttpServletResponse resp) throws IOException {
		buildResponseDocument();
		build(resp);
		LOGGER.info(String.format("-----------------------%s---------------------", "Response Data"));
		LOGGER.info(RESTJsonUtil.GSON.toJson(this.document));
		resp.getWriter().println(RESTJsonUtil.GSON.toJson(this.document));
	}

	/**
	 * Put a single data to response document.
	 * 
	 * @param value
	 *            all kind of classes. But MUST be a single object.
	 */
	public <T> RESTResponseSuccess putData(T value) {
		if (this.data == null) {
			this.data = new ArrayList<>();
		}
		if (value != null) {
			this.data.add(RESTResource.getInstance(value));
		}
		return this;
	}

	public <T> RESTResponseSuccess putData(RESTResource value) {
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		if (value != null) {
			this.data.add(value);
		}
		return this;
	}

	/**
	 * Put many data to response document.
	 * 
	 * @param listValue
	 *            MUST be a collection.
	 */
	public <T> RESTResponseSuccess putData(Collection<T> listValue) {
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		if (listValue != null && !listValue.isEmpty()) {
			for (T value : listValue) {
				this.data.add(RESTResource.getInstance(value));
			}
		}
		return this;
	}

	public RESTResponseSuccess putMeta(String key, Object value) {
		if (this.meta == null) {
			this.meta = new HashMap<String, Object>();
		}
		this.meta.put(key, value);
		return this;
	}

}

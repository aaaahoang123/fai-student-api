package design_java_rest;

import design_java_rest.entity.RESTDocumentSingle;
import design_java_rest.entity.RESTError;
import design_java_rest.util.RESTJsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class RESTResponseError extends RESTResponse {

	private ArrayList<RESTError> errors;

	public RESTResponseError(RESTGeneralError define) {
		this.code = define.code();
	}

	@Override
	void buildResponseDocument() {
		if (this.errors != null && this.errors.size() > 0) {
			this.document = new RESTDocumentSingle.Builder().setErrors(this.errors).build();
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
		resp.getWriter().println(RESTJsonUtil.GSON.toJson(this.document));
	}

	/**
	 * Put errors to response object.
	 */
	public RESTResponseError putErrors(Collection<RESTError> listValue) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		for (RESTError value : listValue) {
			this.errors.add(value);
		}
		return this;
	}

	/**
	 * Put error manually to response object.
	 */
	public RESTResponseError putErrors(int code, String title, String description) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		this.errors.add(
				new RESTError.Builder().setCode(String.valueOf(code)).setTitle(title).setDetail(description).build());
		return this;
	}

}

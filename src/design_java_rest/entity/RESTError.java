package design_java_rest.entity;

public class RESTError {
	private String code;
	private String title;
	private String detail;

	public RESTError() {
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

	protected RESTError(Builder builder) {
		this.code = builder.code;
		this.title = builder.title;
		this.detail = builder.detail;
	}

	public static class Builder {
		String code;
		String title;
		String detail;

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

		public RESTError build() {
			return new RESTError(this);
		}

	}

}

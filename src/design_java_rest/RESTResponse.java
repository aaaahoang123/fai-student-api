package design_java_rest;

import design_java_rest.entity.RESTConstantHttp;
import design_java_rest.entity.RESTDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

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
public abstract class RESTResponse {

	static final Logger LOGGER = Logger.getLogger(RESTResponse.class.getName());

	/**
	 * Response code.
	 */
	int code = 200;
	/**
	 * Response content type, always be "application/json".
	 */
	String contentType = RESTConstantHttp.RESPONSE_CONTENT_TYPE;
	/**
	 * Response character encoding, always be "UTF-8".
	 */
	String encode = RESTConstantHttp.RESPONSE_ENCODING;

	RESTDocument document;

	/**
	 * Build HttpServletResponse response, set content type and another stuff.
	 */
	void build(HttpServletResponse resp) {
		resp.setContentType(this.contentType);
		resp.setCharacterEncoding(this.encode);
		resp.setStatus(this.code);
	}

	/**
	 * This function used to build Response Document. If this is an error
	 * response, it puts error to document object. Else if this is a success
	 * response, it puts data to document object.
	 */
	abstract void buildResponseDocument();

	/**
	 * Make a response to client after build document.
	 */
	public abstract void doResponse(HttpServletResponse resp) throws IOException;

	/**
	 * Use to test response content.
	 */
	public abstract String toJsonString();
}

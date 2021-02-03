package qworks.dataserver.util;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;


/**
 * 
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 */
public final class HttpWriter {

	private static final String MimeType_JSON = "application/json";
	private static final String MimeType_TEXT = "text/plain";
	
	private HttpServletResponse response;

	/**
	 * @param response
	 */
	public HttpWriter(ServletResponse response) {
		this.response = (HttpServletResponse)response;
	}
	
	/**
	 * @param response
	 */
	public HttpWriter(HttpServletResponse response) {
		this.response = response;
	}


	/**
	 * @param error
	 */
	public void writeError(Response.Status error) {
		//response.setStatus(error.toInt());
		try {
			response.sendError(error.getStatusCode());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param error
	 * @param obj
	 */
	public void writeError(Response.Status error, Object obj) {
		//response.setStatus(error.toInt());
		write(MimeType_TEXT, error, obj.toString());
	}
	
	/**
	 * @param error
	 * @param args
	 */
	public void writeError(Response.Status error, Object... args) {
		writeJSON(error, JsonString.fromArgs(args));
	}

	/**
	 * @param args
	 */
	public void writeJSON(Object... args) {
		writeJSON(JsonString.fromArgs(args));
	}

	/**
	 * @param returnCode
	 * @param args
	 */
	public void writeJSON(Response.Status returnCode, Object... args) {
		writeJSON(returnCode, JsonString.fromArgs(args));
	}
	
	/**
	 * @param obj
	 */
	public void writeJSON(JsonString obj) {
		write(MimeType_JSON, Response.Status.OK, obj.toString());
	}
	
	/**
	 * @param returnCode
	 * @param obj
	 */
	public void writeJSON(Response.Status returnCode, JsonString obj) {
		write(MimeType_JSON, returnCode, obj.toString());
	}

	
	/**
	 * @param mimeType
	 * @param returnCode
	 * @param output
	 */
	public void write(String mimeType, Response.Status returnCode, String output) {
		try {
			response.setContentType(mimeType);
			response.setStatus(returnCode.getStatusCode());
			response.getWriter().println(output);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
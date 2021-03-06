/**
 * 
 */
package qworks.dataserver.service.exception;

/**
@author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class ObjectDataException extends ServiceError {

	private static final long serialVersionUID = 698206422280846932L;

	private static final String MSG_DEFAULT = "Invalid or missing required object data";
	private static final String MISSING_FIELD = "Missing required field \"%s\" of type \"%s\"";
	
	/**
	 * 
	 */
	public ObjectDataException() {
		super(MSG_DEFAULT);
	}
	
	/**
	 * @param msg
	 */
	public ObjectDataException(String msg) {
		super(msg);
	}
	
	/**
	 * @param fieldName
	 * @param fieldType
	 */
	public ObjectDataException(String fieldName, Class<?> fieldType) {
		super(String.format(MISSING_FIELD, fieldName, fieldType.getSimpleName()));
	}


}

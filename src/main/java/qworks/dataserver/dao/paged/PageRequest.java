/**
 * 
 */
package qworks.dataserver.dao.paged;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class PageRequest {

	/** */
	public static final int DEFAULT_LIMIT = 500;
	
	private int start = 0;
	private int limit = DEFAULT_LIMIT;
	
	/**
	 * 
	 */
	public PageRequest() {
		
	}

	/**
	 * @param start
	 * @param limit
	 */
	public PageRequest(int start, int limit) {
		super();
		this.start = start;
		this.limit = limit;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the defaultLimit
	 */
	public static int getDefaultLimit() {
		return DEFAULT_LIMIT;
	}

}

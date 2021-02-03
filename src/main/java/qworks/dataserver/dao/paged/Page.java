/**
 * 
 */
package qworks.dataserver.dao.paged;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 * @param <T>
 */
public class Page<T> {

	private int index = 0;
	private int size = 0;
	private T[] contents = null;
	private boolean last = true;
	
	/**
	 * 
	 */
	public Page() {
	}

	/**
	 * @param index
	 * @param contents
	 */
	public Page(int index, T[] contents) {
		super();
		this.index = index;
		this.contents = contents;
	}
	
	/**
	 * Build and returns a PageRequest instance for retrieving the data page next to the actual.
	 * @return
	 */
	public PageRequest nextRequest() {
		PageRequest req = null;
		if (!isLast()) {
			req = new PageRequest(size*(index+1), size);
		}
		return req;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the contents
	 */
	public T[] getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(T[] contents) {
		this.contents = contents;
		setSize(contents.length);
	}

	/**
	 * @return the last
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * @param last the last to set
	 */
	public void setLast(boolean last) {
		this.last = last;
	}

	/**
	 * @param size the size to set
	 */
	private void setSize(int size) {
		this.size = size;
	}


}

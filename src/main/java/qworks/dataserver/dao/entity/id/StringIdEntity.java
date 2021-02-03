/*
 * Version 1.0 3/3/2015
 * 
 */
package qworks.dataserver.dao.entity.id;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Id;
import qworks.dataserver.util.StringUtil;

/**
 * Base class for entities with a String id
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 * @version 1.0
 */
public abstract class StringIdEntity implements IdEntity<String> {

	private static final long serialVersionUID = 3098897372214630390L;
	
	/** */
	private static final String OID_BASE_FMT = "%024s";
	private static final int SID_LENGTH = 11;
	private static final int TIMESTR_LENGTH = 8;


	@Id
	private ObjectId id;

	/**
	 * 
	 */
	public StringIdEntity() {
		super();
	}
	
	/**
	 * @param id
	 */
	public StringIdEntity(String id) {
		super();
		setId(id);
	}

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		String retId = null;
		if (id != null) {
			// Discard the zeroes of the beginning
			retId = StringUtil.last(id.toString(), SID_LENGTH);
		}
		return retId;
		//return id;
	}

	/**
	 * Accepts an String as entity ID but takes only the last 11 digits. 
	 * @param idStr the id to set
	 */
	@Override
	public void setId(String idStr) {
		String aux = String.format(OID_BASE_FMT, trimObjectIdString(idStr,SID_LENGTH));
		this.id = new ObjectId(aux);
		
	}

	
	/**
	 * Gets the actual ObjectId representation of this entity ID
	 * This method must be used for database operations like querying.
	 * @return
	 */
	@Override
	public ObjectId toObjectId() {
		return id;
	}
	 
	
	/**
	 * Gets and concatenate the time and counter sections of an ObjectId string representation
	 * @param str
	 * @param numDigits
	 * @return
	 */
	public static String trimObjectIdString(String str, int numDigits) {
		int d = numDigits - TIMESTR_LENGTH;
		String hexStr = null;
		
		if ((str == null)) {
			return null;
		}
		
		int sz = str.length();
		
		if (str.length() >= numDigits) {
			hexStr = str.substring(0, TIMESTR_LENGTH) + str.substring(sz - d);
		}
		else {
			hexStr = str;
			
		}
		
		return hexStr;
	}
	
}


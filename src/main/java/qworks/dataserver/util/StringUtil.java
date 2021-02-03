/**
 * 
 */
package qworks.dataserver.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author jmgd6647
 *
 */
public class StringUtil {

	
	/**
	 * @param str
	 * @return true si str es null o es una cadena vac�a 
	 */
	public static Boolean isBlank(String str) {
		if ((str == null) || str.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param str
	 * @return true si str es null o es una cadena vac�a 
	 */
	public static String trim(String str) {
		if ((str == null) || str.isEmpty()) {
			return null;
		}
		return str.replaceAll(" ", "");
	}
	
	/**
	 * Returns the String representation of this object when it is not null
	 * If it is null then this method also returns null
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if ((obj == null)) {
			return null;
		}
		else {
			return obj.toString();
		}
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static InputStream toInputStream(String str) {
		InputStream ret = null;
		ret = new ByteArrayInputStream(str.getBytes());
		return ret;
	}
	
	/**
	 * Returns the lastDigits last string digits
	 * @param str
	 * @param lastDigits
	 * @return
	 */
	public static String last(String str, int lastDigits) {
		String hexStr = null;
		Long hexId = null;
		int sz = str.length();
		if (isBlank(str)) {
			return null;
		}
		else if (str.length() >= lastDigits) {
			hexStr = str.substring(sz - lastDigits);
		}
		else {
			hexStr = str;
			
		}
		
		try {
			hexId = Long.parseLong(hexStr, 16);
		}
		catch (NumberFormatException e) {
			return null;
		}
		
		return String.format("%0" + lastDigits + "x", hexId);
		
	}

}

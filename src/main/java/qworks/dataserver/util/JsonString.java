/**
 * 
 */
package qworks.dataserver.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Representation of a JSON message
 * @author jmgarciad
 */
public class JsonString  {

	private static final Logger LOG = LoggerFactory.getLogger(JsonString.class);
	
	/** DATE_FORMAT_STD */
	public static final String DATE_FORMAT_STD 	= "yyyy-MM-dd hh:mm:ss";
	/** DATE_FORMAT_ISO */
	public static final String DATE_FORMAT_ISO 	= "YYYY-MM-DD'T'hh:mm:ss.SSSX"; 
	/** DATE_FORMAT_Z */
	public static final String DATE_FORMAT_Z 		= "yyyy-MM-dd'T'HH:mm:ss.S'Z'";

	private static final SimpleDateFormat stdDateFormat = new SimpleDateFormat(DATE_FORMAT_STD);
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ISO);
	private static final SimpleDateFormat sdfZ = new SimpleDateFormat(DATE_FORMAT_Z);

	
	/**
	 * @return the sdf
	 */
	private static synchronized SimpleDateFormat getStdDateFormat() {
		return stdDateFormat;
	}
	
	
	/**
	 * Build JsonString from pairs String,Object
	 * @param args
	 * @return
	 */
	public static JsonString fromArgs(Object... args) {
		
		try {
			JsonString obj = new JsonString();
			for (int i = 0; i < args.length; i += 2) {
				obj.put((String)args[i], args[i+1]);
			}
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	private Map<String,Object> map = null;
	private String cachedJson = null;
	private boolean isModified = false;
	
	/**
	 * 
	 */
	public JsonString() {
		map = new HashMap<String, Object>();
	}
	
	/**
	 * @param json
	 */
	public JsonString(final String json) {
		this.map = fromString(json);
		this.cachedJson = json;
	}
	
	
	/**
	 * @param map
	 */
	public JsonString(Map<String,Object> map) {
		this.map = map;
	}

	/**
	 * @return
	 */
	public Map<String,Object> getMap( ) {
		return this.map;
	}
	
	/**
	 * @param field
	 * @return
	 */	
	@SuppressWarnings("unchecked")
	public Object getValue(String field) {
		Object val = map.get(field);
		if (val == null) {
			return null;
		}
		if (val instanceof Map<?,?>) {
			return toString((Map<String, Object>)val);
		}
		else if (val instanceof List<?>) {
			return toString((List<?>)val);
		}
		else {
			return val.toString();
		}
	}

	
	/**
	 * @param <T>
	 * @param field
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(String field, Class<T> clazz) {
		Object val = map.get(field);
		if (val == null) {
			return null;
		}
		if (clazz.equals(String.class) ) {
			return (T)val.toString();
		}
		else if (clazz.equals(Integer.class) ) {
			return (T)toInteger(val);
		}
		else if (clazz.equals(Long.class) ) {
			return (T)toLong(val);
		}
		else if (clazz.equals(Date.class) ) {
			return (T)toDate(val);
		}
		return (T)val;
	}


	/**
	 * @param field
	 * @param value
	 */
	public void put(String field, Object value) {
		map.put(field, value);
		isModified = true;
	}

	/**
	 * @return
	 */
	public byte[] getBytes() {
		return toString().getBytes();
	}
	
	/**
	 * @param charset 
	 * @return
	 */
	public byte[] getBytes(Charset charset) {
		return toString().getBytes(charset);
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		if (isModified || (cachedJson == null)) {
			cachedJson = toString(map);
			isModified = false;
		}
		return cachedJson;
	}
	
	/**
	 * Compares two JsonString objects and returns true if equals 
	 * (= same fields at same deep with equal values
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		JsonString json2 = null;
		boolean equals = true;
		if (other instanceof String) {
			json2 = new JsonString((String)other);
		}
		else if (other instanceof Map<?, ?>) {
			json2 = new JsonString((Map<String, Object>)other);
		}
		else if (other instanceof JsonString) {
			json2 = (JsonString)other;
		}
		else {
			return false;
		}
		
		Map<String, Object> map2 = json2.map;
		Set<String> keys = map.keySet();
		if (!keys.equals(map2.keySet())) {
			return false;
		}
		for (String k:keys) {
			equals = false;
			Object val = map.get(k);
			Object val2 =  map2.get(k);
			if (val2 != null) {
				equals = val.equals(val2);
			}
			if (!equals) return false;
		}
				
		return true;
	}


	
	/**
	 * Build a Map instance from a json string representation
	 * @param jsonData 
	 * @return
	 */
	public static Map<String,Object> fromString(String jsonData) {
		Map<String,Object> map = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = new HashMap<String, Object>();
			map = mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			map = null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			map = null;
		} catch (IOException e) {
			e.printStackTrace();
			map = null;
		}
		
		return map;
	}

	
	/**
	 * Convert this object or its string representation to a Date object instance 
	 * @param obj
	 * @return
	 */
	public static Date toDate(Object obj) {
		String dateStr;
		Date ret = null;
		SimpleDateFormat actual = null;
		
		if (!(obj instanceof String)) {
			dateStr = obj.toString();
		}
		
		dateStr = (String)obj;
		
		if (dateStr.endsWith("Z")) {
			actual = sdfZ;
		}
		else {
			actual = sdf;
		}
		
		try {
			ret = actual.parse(dateStr);
		} catch (ParseException e) {
		}
		
		return ret;
	}
	
	
	/**
	 * Convert this object or its string representation to a Date object instance by means of the specified DATE_FORMAT
	 * @param DATE_FORMAT
	 * @param obj
	 * @return
	 */
	public static Date toDate(Object obj, String DATE_FORMAT) {
		String dateStr;
		Date ret = null;
		SimpleDateFormat actual = null;
		
		if (!(obj instanceof String)) {
			dateStr = obj.toString();
		}
		
		dateStr = (String)obj;
		
		actual = new SimpleDateFormat(DATE_FORMAT);
		
		try {
			ret = actual.parse(dateStr);
		} catch (ParseException e) {
		}
		
		return ret;
	}
	
	
	/**
	 * @param in
	 * @return
	 */
	public static Long toLong(Object in) {
		Long ret = null;
		
		// Direct conversion
		if (in instanceof Long) {
			ret = (Long)in;
		}
		// long value transformation from Integer
		else if (in instanceof Integer) {
			Integer aux = (Integer)in;
			ret = aux.longValue();
		}
		// Conversion from String representation
		else {
			String aux = null;
			if (in instanceof String) {
				aux = (String)in;
			}
			else {
				aux = in.toString();
			}
			
			// Parse the aux object holding the String representation of the value 
			try {
				ret = Long.parseLong(aux);
			}
			catch (NumberFormatException e) {
				LOG.error("Error when trying to parse Long value from {}: {}", aux, e.getMessage());
			}
			
		}
		
		return ret;
	}
	
		
	/**
	 * @param in
	 * @return
	 */
	public static Integer toInteger(Object in) {
		Integer ret = null;
		
		// Direct conversion
		if ((in instanceof Long) || (in instanceof Integer)) {
			ret = (Integer)in;
		}
		// Conversion from String representation
		else {
			String aux = null;
			if (in instanceof String) {
				aux = (String)in;
			}
			else {
				aux = in.toString();
			}
			
			// Parse the aux object holding the String representation of the value 
			try {
				ret = Integer.parseInt(aux);
			}
			catch (NumberFormatException e) {
				LOG.error("Error when trying to parse Integer value from {}: {}", aux, e.getMessage());
			}
			
		}
				
		return ret;
	}


	/**
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toStringValue(Object obj) {
			if (obj == null) {
				return null;
			}
			else if (obj instanceof String) {
				return "\"" + (String)obj + "\"";
			}
			else if (obj instanceof Map<?,?>) {
				return toString((Map<String,?>) obj);
			}
			else if (obj instanceof List<?>) {
				return toString((List<?>)obj);
			}
			else if (obj instanceof Date) {
				return "\"" + getStdDateFormat().format((Date)obj) + "\"";
			}
			else {
				Class<?> clazz = obj.getClass();
				LOG.trace("Unmanaged class \"{}\" for object", clazz.getName());
				return obj.toString();
			}
	}



	
	
	//
	//
	//
	//
	//
		
	/**
	 * @param payload
	 * @return
	 */
	private static String toString(Map<String, ?> payload) {
		StringBuilder logStr = new StringBuilder("{");
		if (payload != null) {
			int n = 0;
			for (String field:payload.keySet()) {
				Object val =  payload.get(field);
				if (n++ > 0) logStr.append(", ");
				logStr.append( "\"" + field  + "\" : ");
				logStr.append( toStringValue(val));
			}
		}
		logStr.append("}");
		return logStr.toString();
	}
	
	/**
	 * @param array
	 * @return
	 */
	private static String toString(List<?> array) {
		StringBuilder str = new StringBuilder("[");
		int n = 0;
		for (Object k:array) {
			str.append( toStringValue(k) );
			if (n++ < array.size()) str.append(", ");
		}
		str.append("]");
		return str.toString();
	}

}



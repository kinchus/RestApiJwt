/*
 * Version 1.0 14/3/2015
 * 
 */
package qworks.dataserver.dao.entity.id;

import java.io.Serializable;

import org.bson.types.ObjectId;


/**
 * Generic interface for entities that must provide an id.
 * Id class must be defined in the implementation classes. 
 * 
 * @param <K> 
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 * @version 1.0
 */
public interface IdEntity<K> extends Serializable {
	
	/**
	 * @return
	 */
	K getId();
	
	/**
	 * @param id
	 */
	void setId(K id);

	/**
	 * @param in
	 * @return
	 */
	ObjectId toObjectId();

}

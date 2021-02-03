package qworks.dataserver.service;

import java.io.Serializable;
import java.util.List;

import qworks.dataserver.service.exception.ServiceError;
import qworks.dataserver.service.mapper.ServiceMapper;


/**
 * Interface for business model layer. 
 * It exposes the basic methods for CRUD operations 
 *   
 * @author jmgarcia
 *
 * @param <T>
 * @param <K>
 */
public interface Service<T, K extends Serializable> extends Serializable {

	
	/**
	 * @return
	 */
	default ServiceMapper getMapper()  {
		return ServiceMapper.INSTANCE;
	}
	
	/**
	 * @param object
	 * @throws ServiceError
	 */
	public void validate(T object) throws ServiceError;
	
	/**
	 * @param id
	 * @return
	 * @throws ServiceError 
	 */
	T getById(K id) throws ServiceError;

	/**
	 * @return
	 * @throws ServiceError 
	 */
	List<T> getAll() throws ServiceError;
	
	/**
	 * @param data
	 * @return
	 * @throws ServiceError 
	 */
	T create(T data) throws ServiceError;

	/**
	 * @param data
	 * @throws ServiceError 
	 */
	void update(T data) throws ServiceError;

	/**
	 * @param id
	 * @throws ServiceError 
	 */
	void delete(K id) throws ServiceError;

}
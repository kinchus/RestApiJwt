package qworks.dataserver.dao.entity.id;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Id;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class LongIdEntity implements IdEntity<Long> {

	private static final long serialVersionUID = 8590499460575162390L;

	@Id
	private Long id;
	
	/**
	 */
	public LongIdEntity() {}

	
	/**
	 * @param id
	 */
	public LongIdEntity(Long id) {
		setId(id);
	}

	/**
	 * @return
	 * @see qworks.dataserver.dao.entity.id.IdEntity#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 * @see qworks.dataserver.dao.entity.id.IdEntity#setId(java.lang.Object)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return
	 * @see qworks.dataserver.dao.entity.id.IdEntity#toObjectId()
	 */
	@Override
	public ObjectId toObjectId() {
		String hex = String.format("%024d", id);
		return new ObjectId(hex);
	}

}

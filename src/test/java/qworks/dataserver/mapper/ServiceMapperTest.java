package qworks.dataserver.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import qworks.dataserver.dao.entity.RoleEntity;
import qworks.dataserver.dao.entity.UserEntity;
import qworks.dataserver.model.Role;
import qworks.dataserver.model.User;
import qworks.dataserver.service.mapper.ServiceMapper;

/**
 * @author <a href="mailto:garciadjx@gmail.com">J.M. Garcia</a>
 *
 */
public class ServiceMapperTest {
	
	/**
	 * 
	 */
	@Test
	public void testNull() {
		
	}

	/**
	 * 
	 */
	@Test
	public void testMapUserDto() {
		User dto = createUserDto();
		UserEntity ent = ServiceMapper.INSTANCE.mapUser(dto);
		
		assertNotNull( ent );
		
		assertEquals( dto.getId(), ent.getId() );
		assertEquals( dto.getUsername(), ent.getUsername() );
		assertEquals( dto.getEmail(), ent.getEmail() );
		assertEquals( dto.getFirstName(), ent.getFirstName() );
		assertEquals( dto.getLastName(), ent.getLastName() );
		assertEquals( dto.getAccountId() , ent.getAccountId() );
		
		List<Role> dtoRoles = dto.getRoles();
		for (Role role:dtoRoles) {
			RoleEntity rEnt = ServiceMapper.INSTANCE.mapRole(role);
			assertNotNull( rEnt );
			assertEquals( role.getName(), rEnt.getName() );
		}
		
		
	}
	
	/**
	 * 
	 */
	@Test
	public void testMapUserEntity() {
		UserEntity ent = createUserEntity();
		User dto = ServiceMapper.INSTANCE.mapUser(ent);
		
		assertNotNull( dto );
		
		assertEquals( dto.getId(), ent.getId() );
		assertEquals( dto.getUsername(), ent.getUsername() );
		assertEquals( dto.getEmail(), ent.getEmail() );
		assertEquals( dto.getFirstName(), ent.getFirstName() );
		assertEquals( dto.getLastName(), ent.getLastName() );
		assertEquals( dto.getAccountId() , ent.getAccountId() );
		
		List<RoleEntity> entRoles = ent.getRoles();
		for (RoleEntity roleEnt:entRoles) {
			Role roleDto = ServiceMapper.INSTANCE.mapRole(roleEnt);
			assertNotNull( roleDto );
			assertEquals( roleEnt.getName(), roleDto.getName() );
		}
	}
	
	
	
	
	
	private User createUserDto() {
		
		
		User ret = new User();
		ret.setUsername("test");
		ret.setEmail("test@test.com");
		ret.setFirstName("test");
		ret.setLastName("test");
		ret.setAccountId("001");
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("TEST1"));
		roles.add(new Role("TEST2"));
		roles.add(new Role("TEST3"));
		
		ret.setRoles(roles);
		
		return ret;
	}
	
	
	private UserEntity createUserEntity() {
		UserEntity ret = new UserEntity();
		ret.setUsername("test");
		ret.setEmail("test@test.com");
		ret.setFirstName("test");
		ret.setLastName("test");
		ret.setAccountId("001");
		
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(new RoleEntity("TEST1"));
		roles.add(new RoleEntity("TEST2"));
		roles.add(new RoleEntity("TEST3"));
		
		ret.setRoles(roles);
		
		return ret;
	}
	
}

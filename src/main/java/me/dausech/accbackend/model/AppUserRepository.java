package me.dausech.accbackend.model;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
// @RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface AppUserRepository extends Repository<AppUser, Long> {
	AppUser save(AppUser user);

	AppUser findByName(String name);
}

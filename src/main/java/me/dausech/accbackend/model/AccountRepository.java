package me.dausech.accbackend.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>{
	
//	@Override
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	public Account save(Account account);
//	
}

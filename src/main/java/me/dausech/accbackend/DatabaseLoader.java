package me.dausech.accbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import me.dausech.accbackend.model.AppUser;
import me.dausech.accbackend.model.AppUserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final AppUserRepository repository;

	@Autowired
	public DatabaseLoader(AppUserRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {

		AppUser douglas = this.repository.save(new AppUser("douglas", "secret",	"ROLE_ADMIN"));
	
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("douglas", "secret", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));

		SecurityContextHolder.clearContext();
	}
}
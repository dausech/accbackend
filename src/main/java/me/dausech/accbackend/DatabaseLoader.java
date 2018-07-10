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

		this.repository.save(new AppUser("douglas", "secret", "ROLE_ADMIN"));
		this.repository.save(new AppUser("user", "secret", "ROLE_USER"));

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("douglas",
				"xxx", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user",
				"xxx", AuthorityUtils.createAuthorityList("ROLE_USER")));

		SecurityContextHolder.clearContext();
	}
}
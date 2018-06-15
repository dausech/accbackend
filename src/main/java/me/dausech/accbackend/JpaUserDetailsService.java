package me.dausech.accbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import me.dausech.accbackend.model.AppUser;
import me.dausech.accbackend.model.AppUserRepository;

@Component
public class JpaUserDetailsService implements UserDetailsService {

	private final AppUserRepository repository;

	@Autowired
	public JpaUserDetailsService(AppUserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		AppUser appUser = this.repository.findByName(name);
		return new User(appUser.getName(), appUser.getPassword(),
				AuthorityUtils.createAuthorityList(appUser.getRoles()));
	}

}
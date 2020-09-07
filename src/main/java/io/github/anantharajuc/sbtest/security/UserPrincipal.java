package io.github.anantharajuc.sbtest.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import io.github.anantharajuc.sbtest.auth.Role;
import io.github.anantharajuc.sbtest.auth.User;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserPrincipal implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	private final User user;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal(User user) 
	{
		log.info("-----> user -           : "+user.toString());
		
        this.user = user;
        
        /*String[] permissions = user.getRoles().stream()
					                .flatMap(role -> role.getPermissions().stream())
					                .map(permission -> permission.getName())
					                .toArray(String[]::new);
        
        for (int i = 0; i < permissions.length; i++) 
        {
        	log.info("-----> permissions[i]                : "+permissions[i]);
        }*/
        
        Set<String> roleAndPermissions = new HashSet<>();
		
		List<Role> roles = user.getRoles();
		
		for (Role role : roles)
		{
			roleAndPermissions.add(role.getName());
			
			log.info("-----> Role                : "+role.getName());
			
			for(int i = 0; i<role.getPermissions().size(); i++)
			{
				log.info("-----> Permission          : "+role.getPermissions().get(i).getName());
				
				roleAndPermissions.add(role.getPermissions().get(i).getName());
			}
		}
		
		String[] roleNames = new String[roleAndPermissions.size()];
		
		log.info("-----> Roles & Permissions : "+roleAndPermissions.toString());			
		
		//Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roleAndPermissions.toArray(roleNames));
        
        this.authorities = AuthorityUtils.createAuthorityList(roleAndPermissions.toArray(roleNames));
    }
	
	public User getUser() 
	{
        return user;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return authorities;
	}

	@Override
	public String getPassword() 
	{
		return user.getPassword();
	}

	@Override
	public String getUsername() 
	{
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isEnabled() 
	{
		return true;
	}

}
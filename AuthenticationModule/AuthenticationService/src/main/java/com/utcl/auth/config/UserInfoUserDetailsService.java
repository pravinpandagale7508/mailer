package com.utcl.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.utcl.auth.entity.UserInfo;
import com.utcl.auth.repository.UserInfoRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
	@Autowired
    private UserInfoRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		// load user from service
		UserInfo info = new UserInfo(1, "pravin12", "pandagalee12@gmail.com", "$2a$10$vWnS.q3Wx.oLaW8MydJ7F.MMYejIR1tRo7oYy1Ycwqx/9LIu0gxOq", "ROLE_ADMIN");
		UserInfoUserDetails details = new UserInfoUserDetails(info) ;
//		Optional<UserInfo> userInfo = repository.findByName(username);
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

		
		
		return details;
	}

   
}

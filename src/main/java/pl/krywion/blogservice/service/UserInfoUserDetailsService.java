package pl.krywion.blogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.krywion.blogservice.config.UserInfoUserDetails;
import pl.krywion.blogservice.model.UserInfo;
import pl.krywion.blogservice.repository.UserInfoRepository;

import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);

        return userInfo
                .map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found" + username));
    }
}

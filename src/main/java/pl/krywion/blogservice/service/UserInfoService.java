package pl.krywion.blogservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krywion.blogservice.model.UserInfo;
import pl.krywion.blogservice.repository.UserInfoRepository;

import java.util.List;

@Service
public class UserInfoService {
   private final UserInfoRepository userInfoRepository;
   private final PasswordEncoder passwordEncoder;


    public UserInfoService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfo addUser(UserInfo userInfo) {
        if(userInfoRepository.findByName(userInfo.getName()).isPresent()) {
            return null;
        } else {
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            userInfoRepository.save(userInfo);
            System.out.println("User added");
            return userInfo;
        }
    }
    public UserInfo addEditor(String name, String password) {
        if(userInfoRepository.findByName(name).isPresent()) {
            return null;
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(name);
            userInfo.setPassword(passwordEncoder.encode(password));
            userInfo.setRoles("ROLE_EDITOR");
            userInfoRepository.save(userInfo);
            System.out.println("User added");
            return userInfo;
        }
    }
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAllByRoles("ROLE_EDITOR");
    }

    public boolean deleteUser(Long id) {
        if(userInfoRepository.findById(id).isPresent()) {
            userInfoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }



}

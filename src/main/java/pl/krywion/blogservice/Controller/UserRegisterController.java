package pl.krywion.blogservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.krywion.blogservice.model.UserInfo;
import pl.krywion.blogservice.service.UserInfoService;


@Controller
public class UserRegisterController {

    private final UserInfoService userInfoService;

    public UserRegisterController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/blog/register")
    public ResponseEntity<UserInfo> addNewUser(@RequestBody UserInfo userInfo){
        UserInfo userInfoEncrypted = userInfoService.addUser(userInfo);
        return new ResponseEntity<UserInfo>(userInfoEncrypted, HttpStatus.CREATED);
    }
}

package controller;


import entity.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;


    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseInfo login(@RequestParam("userName") String userName,
                              @RequestParam("password") String password) throws Exception{
        ResponseInfo info = new ResponseInfo();

        List<User> userList = userRepository.findByUsername(userName);
        User currentUser = userList.size()>0? userList.get(0) : null;
        if(null == currentUser){
            info.setFailWithInfo("User doesn't exist");
            return info;
        }

        if(!currentUser.password.equals(password)){
            info.setFailWithInfo("Username/password doesn't match");
            return info;
        } else {
            info.setDetailWithInfo(String.valueOf(currentUser.uid),
                    currentUser.name,
                    String.valueOf(currentUser.isadmin));
            return info;
        }
    }

    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUser() throws Exception{
        List<User> users = userRepository.findAll();
        for (int i=0; i<users.size(); i++){
            users.get(i).password = "";
        }
        return users;
    }

    /**
     * 获取用户
     * @param uid
     * @return
     */
    @RequestMapping(value = "/user/{uid}/user", method = RequestMethod.GET)
    public User fetchUser(@PathVariable Long uid) throws Exception{
        User user = userRepository.findOne(uid);
        return user;
    }
}

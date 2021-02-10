package com.mmall.service.Impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("User Not Found");
        }

        //todo login with MD5
        User user = userMapper.selectLogin(username, password);
        if(user == null) {
            return ServerResponse.createByErrorMessage("Wrong Password");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("Success", user);
    }
}

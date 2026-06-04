package com.xihongshi.expert.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xihongshi.expert.common.Constants;
import com.xihongshi.expert.entity.User;
import com.xihongshi.expert.mapper.UserMapper;
import com.xihongshi.expert.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> login(String phone, String code) {
        if (code == null || !code.matches("\\d{4}")) {
            throw new RuntimeException("验证码必须为四位数字");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setPasswordHash("sms_login");
            user.setRole(Constants.ROLE_EXPERT);
            userMapper.insert(user);
        }

        String token = jwtUtil.generateToken(user.getId(), user.getPhone(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    public User getUserInfo(Long userId) {
        return userMapper.selectById(userId);
    }
}

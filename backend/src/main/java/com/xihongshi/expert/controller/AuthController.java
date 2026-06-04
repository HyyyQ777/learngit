package com.xihongshi.expert.controller;

import com.xihongshi.expert.common.Result;
import com.xihongshi.expert.dto.LoginRequest;
import com.xihongshi.expert.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> result = authService.login(request.getPhone(), request.getCode());
        return Result.success(result);
    }

    @PostMapping("/send-code")
    public Result<?> sendCode(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        if (phone == null || phone.isEmpty()) {
            return Result.error("手机号不能为空");
        }
        return Result.success("请任意输入四位数字");
    }

    @GetMapping("/me")
    public Result<?> getUserInfo(@RequestAttribute("userId") Long userId) {
        return Result.success(authService.getUserInfo(userId));
    }
}

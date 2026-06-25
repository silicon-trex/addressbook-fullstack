package com.addressbook.controller;

import com.addressbook.model.User;
import com.addressbook.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResult<User> login(@RequestParam String username,
                                  @RequestParam String password,
                                  HttpSession session) {
        User user = userService.login(username, password);
        if (user != null) {
            // 不把密码返回前端
            user.setPassword(null);
            session.setAttribute("user", user);
            return ApiResult.success(user);
        }
        return ApiResult.error("用户名或密码错误！");
    }

    @PostMapping("/register")
    public ApiResult<?> register(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String password2,
                                  @RequestParam Integer age) {
        if (!password.equals(password2)) {
            return ApiResult.error("两次密码不一致，请重新输入！");
        }
        if (userService.checkUsernameExists(username)) {
            return ApiResult.error("用户名已经存在，请重新填写！");
        }
        User user = new User(username, password, age);
        if (userService.register(user)) {
            return ApiResult.success(null);
        }
        return ApiResult.error("注册失败，请重试！");
    }

    @GetMapping("/logout")
    public ApiResult<?> logout(HttpSession session) {
        session.invalidate();
        return ApiResult.success(null);
    }

    @GetMapping("/me")
    public ApiResult<User> me(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ApiResult.error("未登录");
        }
        user.setPassword(null);
        return ApiResult.success(user);
    }
}

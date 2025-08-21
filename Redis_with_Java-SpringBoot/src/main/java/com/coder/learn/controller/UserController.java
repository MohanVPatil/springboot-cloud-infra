package com.coder.learn.controller;

import com.coder.learn.entity.FullUserInfoRequest;
import com.coder.learn.entity.User;
import com.coder.learn.entity.UserAccess;
import com.coder.learn.entity.UserActivityLog;
import com.coder.learn.repository.UserAccessRepository;
import com.coder.learn.repository.UserActivityLogRepository;
import com.coder.learn.repository.UserRepository;
import com.coder.learn.service.UserService;
import com.coder.learn.service.UserSpecificService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSpecificService userSpecificService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("redisObjectTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    private UserActivityLogRepository userActivityLogRepository;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        // return userService.getUserById(id);
        return userService.findUserById(id);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/editUser/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        // User modifiedUser = userService.editUserById(id, user);
        User modifiedUser = userService.updateUserById(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUser);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User deleteUser = userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteUser);
    }

    @PostMapping("/addUser/full-info")
    public ResponseEntity<String> saveFullUserInfo(@RequestBody FullUserInfoRequest request) {
        User user = request.getUser();
        UserAccess access = request.getUserAccess();
        UserActivityLog activityLog = request.getUserActivityLog();

        userRepository.save(user);
        userAccessRepository.save(access);
        userActivityLogRepository.save(activityLog);

        redisTemplate.opsForValue().set("USER_" + user.getUserId(), user, 10, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("ACCESS_" + user.getUserId(), access, 10, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("LOG_" + user.getUserId(), activityLog, 10, TimeUnit.MINUTES);

        return ResponseEntity.ok("Stored in cache successfully.");
    }

    @PostMapping("/addOrUpdateUser")
    public ResponseEntity<String> syncUserFromCacheOrInsert(@RequestBody FullUserInfoRequest request) {
        String responseMessage = userSpecificService.syncUserFromCacheOrInsert(request);
        return ResponseEntity.ok(responseMessage);
    }
}

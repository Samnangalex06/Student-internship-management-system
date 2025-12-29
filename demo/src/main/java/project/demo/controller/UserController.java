package project.demo.controller;
import project.demo.Model.CreateUserRequest;
import project.demo.entity.User;
import project.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userService.createUserWithRole(user, request.getRoleName());
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
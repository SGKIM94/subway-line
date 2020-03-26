package atdd.station.controller;

import atdd.user.application.UserService;
import atdd.user.application.dto.CreateUserRequestView;
import atdd.user.application.dto.LoginUserRequestView;
import atdd.user.application.dto.UserResponseView;
import atdd.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseView> createUser(@RequestBody CreateUserRequestView view) {
        final User savedUser = userService.save(view.toUser());
        return ResponseEntity.created(URI.create("/users/" + savedUser.getId()))
                .body(new UserResponseView(savedUser));
    }

    @GetMapping("/me")
    public ResponseEntity retrieveUser(@LoginUser User user) {
        User persistUser = userService.findUserByEmail(user.getEmail());
        return ResponseEntity.ok().body(new UserResponseView(persistUser));
    }

    @GetMapping("/login")
    public ResponseEntity login(@LoginUser LoginUserRequestView user) {
        return ResponseEntity.ok().build();
    }
}

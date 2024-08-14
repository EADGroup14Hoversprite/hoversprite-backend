package enterprise.hoversprite.modules.user.controller;

import java.util.Optional;

import enterprise.hoversprite.modules.user.dtos.SaveUserRequestDTO;
import enterprise.hoversprite.modules.user.dtos.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Only for admin
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody SaveUserRequestDTO dto) {
        return new ResponseEntity<>(userService.saveUser(dto.toModel()).toDto(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponseDTO>> getUserById(@RequestParam Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        return new ResponseEntity<>(optionalUser.map(User::toDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody SaveUserRequestDTO dto) {
        return new ResponseEntity<>(userService.saveUser(dto.toModel()).toDto(), HttpStatus.OK);
    }

    // Only for admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

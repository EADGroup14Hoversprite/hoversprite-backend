package enterprise.hoversprite.modules.user.controller;

import java.util.Optional;

import enterprise.hoversprite.modules.user.dtos.SaveUserRequestDTO;
import enterprise.hoversprite.modules.user.dtos.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public UserResponseDTO createUser(@RequestBody SaveUserRequestDTO dto) {
    return userService.saveUser(dto.toModel()).toDto();
  }

  @GetMapping("/{id}")
  public Optional<UserResponseDTO> getUserById(@PathVariable Long id) {
    Optional<User> optionalUser = userService.getUserById(id);
    return optionalUser.map(User::toDto);
  }

  @PutMapping("/{id}")
  public UserResponseDTO updateUser(@RequestBody SaveUserRequestDTO dto) {
    return userService.saveUser(dto.toModel()).toDto();
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }

}

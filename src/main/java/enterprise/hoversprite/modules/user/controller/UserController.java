package enterprise.hoversprite.modules.user.controller;

import enterprise.hoversprite.modules.user.dtos.DeleteUserResponseDTO;
import enterprise.hoversprite.modules.user.dtos.GetUserResponseDTO;
import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponseDTO> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(new GetUserResponseDTO("User data found", user.toDto()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new GetUserResponseDTO(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    //Authorized for admin
    //Authorized for user with jwt token id == param
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserResponseDTO> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(new DeleteUserResponseDTO("User deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new DeleteUserResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}

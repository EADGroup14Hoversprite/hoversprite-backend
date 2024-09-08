package internal.controller;

import internal.dtos.GetUserResponseDTO;
import shared.services.UserService;
import shared.dtos.user.UserDTO;
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
class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    ResponseEntity<GetUserResponseDTO> getUserById(@PathVariable Long id) throws Exception {
        UserDTO dto = userService.getUserById(id);
        return new ResponseEntity<>(new GetUserResponseDTO("User data found", dto), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @DeleteMapping("/{id}")
//    ResponseEntity<GetUserResponseDTO> deleteUserById(@PathVariable Long id)  throws Exception {
//        UserDTO dto = userService.getUserInfoById(id);
//        return new ResponseEntity<>(new GetUserResponseDTO("User data found", dto), HttpStatus.OK);
//    }
}

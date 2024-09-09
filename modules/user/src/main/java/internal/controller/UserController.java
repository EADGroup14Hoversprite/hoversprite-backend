package internal.controller;

import internal.dtos.GetUserResponseDto;
import internal.service.UserServiceImpl;
import shared.dtos.UserDto;
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
    private UserServiceImpl userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    ResponseEntity<GetUserResponseDto> getUserById(@PathVariable Long id) throws Exception {
        UserDto dto = userService.getUserById(id);
        return new ResponseEntity<>(new GetUserResponseDto("User data found", dto), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @DeleteMapping("/{id}")
//    ResponseEntity<GetUserResponseDTO> deleteUserById(@PathVariable Long id)  throws Exception {
//        UserDTO dto = userService.getUserInfoById(id);
//        return new ResponseEntity<>(new GetUserResponseDTO("User data found", dto), HttpStatus.OK);
//    }
}

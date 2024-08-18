package controller;

import interfaces.IUserService;
import dtos.UserInfoDTO;
import dtos.response.GetUserResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "internal.model.User API")
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
            UserInfoDTO dto = userService.getUserInfoById(id);
            return new ResponseEntity<>(new GetUserResponseDTO("internal.model.User data found", dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new GetUserResponseDTO(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }
}

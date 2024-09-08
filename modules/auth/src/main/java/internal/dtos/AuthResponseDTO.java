package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.dtos.auth.AuthDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String message;

    private AuthDTO dto;
}

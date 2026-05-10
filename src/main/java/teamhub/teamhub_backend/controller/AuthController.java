package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamhub.teamhub_backend.dto.LoginRequestDTO;
import teamhub.teamhub_backend.dto.LoginResponseDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        // Implementação mockada de login - Substitua pela integração real com banco de dados/JWT
        if ("admin".equals(loginRequest.getUsername()) && "admin".equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponseDTO("Mocked-JWT-Token-12345", "Login bem-sucedido!"));
        }
        return ResponseEntity.status(401).body(new LoginResponseDTO(null, "Credenciais inválidas"));
    }
}

package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamhub.teamhub_backend.dto.LoginRequestDTO;
import teamhub.teamhub_backend.dto.LoginResponseDTO;

import teamhub.teamhub_backend.repository.UserRepository;
import teamhub.teamhub_backend.security.JwtUtil;
import teamhub.teamhub_backend.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!user.getStatus()) {
                return ResponseEntity.status(403).body(new LoginResponseDTO(null, "Usuário inativo"));
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getAccessLevel());
                return ResponseEntity.ok(new LoginResponseDTO(token, "Login bem-sucedido!"));
            }
        }

        return ResponseEntity.status(401).body(new LoginResponseDTO(null, "Credenciais inválidas"));
    }
}

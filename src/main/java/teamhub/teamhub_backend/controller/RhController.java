package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import teamhub.teamhub_backend.dto.UserRequestDTO;
import teamhub.teamhub_backend.dto.UserResponseDTO;
import teamhub.teamhub_backend.model.User;
import teamhub.teamhub_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('rh')")
public class RhController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RhController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty() ||
            request.getEmail() == null || request.getEmail().trim().isEmpty() ||
            request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Missing required fields (username, email, password).");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setAccessLevel(request.getAccessLevel() != null ? request.getAccessLevel() : "colaborador");
        user.setStatus(request.getStatus() != null ? request.getStatus() : true);

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserResponseDTO(savedUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userRepository.findByStatusTrue();
        List<UserResponseDTO> response = users.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<UserResponseDTO>> readAll() {
        return getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return userRepository.findByIdAndStatusTrue(id)
                .map(user -> ResponseEntity.ok(new UserResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserRequestDTO request) {
        Optional<User> userOptional = userRepository.findByIdAndStatusTrue(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOptional.get();

        if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            Optional<User> existing = userRepository.findByUsername(request.getUsername());
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                return ResponseEntity.badRequest().body("Username already exists.");
            }
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getAccessLevel() != null && !request.getAccessLevel().trim().isEmpty()) {
            user.setAccessLevel(request.getAccessLevel());
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserResponseDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<User> userOptional = userRepository.findByIdAndStatusTrue(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOptional.get();
        user.setStatus(false);
        userRepository.save(user);
        return ResponseEntity.ok("User status updated to false successfully.");
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserLegacy(@PathVariable Integer id) {
        return deleteUser(id);
    }
}

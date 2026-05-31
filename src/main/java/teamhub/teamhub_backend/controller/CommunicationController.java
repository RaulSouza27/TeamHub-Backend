package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import teamhub.teamhub_backend.dto.StatementRequestDTO;
import teamhub.teamhub_backend.dto.StatementResponseDTO;
import teamhub.teamhub_backend.model.Statement;
import teamhub.teamhub_backend.model.User;
import teamhub.teamhub_backend.repository.StatementRepository;
import teamhub.teamhub_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/statements")
public class CommunicationController {

    private final StatementRepository statementRepository;
    private final UserRepository userRepository;

    public CommunicationController(StatementRepository statementRepository, UserRepository userRepository) {
        this.statementRepository = statementRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @GetMapping("/feed")
    public ResponseEntity<List<StatementResponseDTO>> getFeed() {
        List<Statement> comunicados = statementRepository.findAllByOrderByCreatedAtDesc();
        List<StatementResponseDTO> response = comunicados.stream()
                .map(StatementResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<String> postComment(@PathVariable Long postId) {
        return ResponseEntity.ok("Comentário na postagem " + postId + " criado com sucesso.");
    }

    @PreAuthorize("hasAnyAuthority('gestor', 'rh')")
    @PostMapping("/team-announcements")
    public ResponseEntity<String> postTeamAnnouncement() {
        return ResponseEntity.ok("Aviso para a equipe publicado com sucesso.");
    }

    @PreAuthorize("hasAuthority('rh')")
    @PostMapping("/global-announcements")
    public ResponseEntity<?> postGlobalAnnouncement(@RequestBody StatementRequestDTO request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty() ||
            request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Título e conteúdo são obrigatórios.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário autenticado não encontrado.");
        }

        Statement comunicado = new Statement();
        comunicado.setTitle(request.getTitle());
        comunicado.setContent(request.getContent());
        comunicado.setAuthor(userOptional.get());

        Statement saved = statementRepository.save(comunicado);
        return ResponseEntity.ok(new StatementResponseDTO(saved));
    }

    @PreAuthorize("hasAuthority('rh')")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        return ResponseEntity.ok("Comentário " + commentId + " apagado com sucesso pelo RH.");
    }
}

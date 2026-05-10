package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comunicacao")
public class ComunicacaoController {

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @GetMapping("/feed")
    public ResponseEntity<String> getFeed() {
        return ResponseEntity.ok("Feed público de notícias da empresa.");
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
    public ResponseEntity<String> postGlobalAnnouncement() {
        return ResponseEntity.ok("Aviso global para toda a empresa publicado com sucesso (Apenas RH).");
    }

    @PreAuthorize("hasAuthority('rh')")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        return ResponseEntity.ok("Comentário " + commentId + " apagado com sucesso pelo RH.");
    }
}

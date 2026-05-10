package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @GetMapping("/me")
    public ResponseEntity<String> getMyDashboard() {
        return ResponseEntity.ok("Métricas individuais do usuário logado.");
    }

    @PreAuthorize("hasAnyAuthority('gestor', 'rh')")
    @GetMapping("/team")
    public ResponseEntity<String> getTeamDashboard() {
        return ResponseEntity.ok("Métricas coletivas da equipe gerenciada.");
    }

    @PreAuthorize("hasAuthority('rh')")
    @GetMapping("/admin")
    public ResponseEntity<String> getAdminDashboard() {
        return ResponseEntity.ok("Métricas completas de toda a empresa (Turnover, ativos, etc).");
    }
}

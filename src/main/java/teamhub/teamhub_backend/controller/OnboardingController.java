package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @GetMapping("/my-tasks")
    public ResponseEntity<String> getMyTasks() {
        return ResponseEntity.ok("Lista de tarefas de onboarding do usuário.");
    }

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @PostMapping("/tasks/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable Long taskId) {
        return ResponseEntity.ok("Tarefa de onboarding " + taskId + " concluída.");
    }

    @PreAuthorize("hasAnyAuthority('gestor', 'rh')")
    @GetMapping("/team-progress")
    public ResponseEntity<String> getTeamProgress() {
        return ResponseEntity.ok("Progresso de onboarding dos subordinados.");
    }

    @PreAuthorize("hasAuthority('rh')")
    @PostMapping("/trails")
    public ResponseEntity<String> createTrail() {
        return ResponseEntity.ok("Nova trilha de onboarding criada pelo RH.");
    }

    @PreAuthorize("hasAuthority('rh')")
    @GetMapping("/global-progress")
    public ResponseEntity<String> getGlobalProgress() {
        return ResponseEntity.ok("Progresso de onboarding de todos os funcionários.");
    }
}

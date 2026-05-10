package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    @PreAuthorize("hasAuthority('rh')")
    @PostMapping("/invite")
    public ResponseEntity<String> inviteCandidate() {
        return ResponseEntity.ok("Convite para admissão enviado ao candidato com sucesso.");
    }

    @PreAuthorize("hasAuthority('rh')")
    @GetMapping("/pending")
    public ResponseEntity<String> getPendingAdmissions() {
        return ResponseEntity.ok("Lista de admissões pendentes aguardando revisão do RH.");
    }

    @PreAuthorize("hasAuthority('rh')")
    @PostMapping("/{admissionId}/approve")
    public ResponseEntity<String> approveAdmission(@PathVariable Long admissionId) {
        return ResponseEntity.ok("Admissão " + admissionId + " aprovada. Candidato agora é um colaborador.");
    }

    @PreAuthorize("hasAuthority('rh')")
    @PostMapping("/{admissionId}/reject")
    public ResponseEntity<String> rejectAdmission(@PathVariable Long admissionId) {
        return ResponseEntity.ok("Admissão " + admissionId + " recusada (Documentos inválidos/ilegíveis).");
    }
}

package teamhub.teamhub_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import teamhub.teamhub_backend.dto.DocumentSubmissionRequestDTO;
import teamhub.teamhub_backend.dto.DocumentSubmissionResponseDTO;
import teamhub.teamhub_backend.model.DocumentSubmission;
import teamhub.teamhub_backend.model.User;
import teamhub.teamhub_backend.repository.DocumentSubmissionRepository;
import teamhub.teamhub_backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    private final DocumentSubmissionRepository documentSubmissionRepository;
    private final UserRepository userRepository;

    public AdmissionController(DocumentSubmissionRepository documentSubmissionRepository, UserRepository userRepository) {
        this.documentSubmissionRepository = documentSubmissionRepository;
        this.userRepository = userRepository;
    }

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

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @PostMapping("/submit")
    public ResponseEntity<?> submitDocuments(@RequestBody DocumentSubmissionRequestDTO request) {
        if (request.getRgBase64() == null || request.getRgBase64().trim().isEmpty() ||
            request.getCpfBase64() == null || request.getCpfBase64().trim().isEmpty() ||
            request.getWorkCardBase64() == null || request.getWorkCardBase64().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Todos os documentos (RG, CPF, Carteira de Trabalho) são obrigatórios.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário autenticado não encontrado.");
        }
        User user = userOptional.get();

        // Verifica se já existe um envio para este usuário
        Optional<DocumentSubmission> existingOpt = documentSubmissionRepository.findByUserId(user.getId());
        DocumentSubmission submission;
        if (existingOpt.isPresent()) {
            submission = existingOpt.get();
        } else {
            submission = new DocumentSubmission();
            submission.setUser(user);
        }

        submission.setRgBase64(request.getRgBase64());
        submission.setCpfBase64(request.getCpfBase64());
        submission.setWorkCardBase64(request.getWorkCardBase64());
        submission.setStatus("PENDENTE");
        submission.setSubmittedAt(LocalDateTime.now());

        DocumentSubmission saved = documentSubmissionRepository.save(submission);
        return ResponseEntity.ok(new DocumentSubmissionResponseDTO(saved));
    }

    @PreAuthorize("hasAnyAuthority('colaborador', 'gestor', 'rh')")
    @GetMapping("/my-submission")
    public ResponseEntity<?> getMySubmission() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário autenticado não encontrado.");
        }
        User user = userOptional.get();

        Optional<DocumentSubmission> submissionOpt = documentSubmissionRepository.findByUserId(user.getId());
        if (submissionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DocumentSubmissionResponseDTO(submissionOpt.get()));
    }

    @PreAuthorize("hasAuthority('rh')")
    @GetMapping("/all-submissions")
    public ResponseEntity<List<DocumentSubmissionResponseDTO>> getAllSubmissions() {
        List<DocumentSubmission> submissions = documentSubmissionRepository.findAllByOrderBySubmittedAtDesc();
        List<DocumentSubmissionResponseDTO> response = submissions.stream()
                .map(DocumentSubmissionResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('rh')")
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveSubmission(@PathVariable Integer id) {
        Optional<DocumentSubmission> submissionOpt = documentSubmissionRepository.findById(id);
        if (submissionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Submissão não encontrada.");
        }
        DocumentSubmission submission = submissionOpt.get();
        submission.setStatus("APROVADO");
        DocumentSubmission saved = documentSubmissionRepository.save(submission);
        return ResponseEntity.ok(new DocumentSubmissionResponseDTO(saved));
    }

    @PreAuthorize("hasAuthority('rh')")
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectSubmission(@PathVariable Integer id) {
        Optional<DocumentSubmission> submissionOpt = documentSubmissionRepository.findById(id);
        if (submissionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Submissão não encontrada.");
        }
        DocumentSubmission submission = submissionOpt.get();
        submission.setStatus("REJEITADO");
        DocumentSubmission saved = documentSubmissionRepository.save(submission);
        return ResponseEntity.ok(new DocumentSubmissionResponseDTO(saved));
    }
}

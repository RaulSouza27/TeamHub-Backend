package teamhub.teamhub_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_submissions")
public class DocumentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "rg_base64", nullable = false, columnDefinition = "TEXT")
    private String rgBase64;

    @Column(name = "cpf_base64", nullable = false, columnDefinition = "TEXT")
    private String cpfBase64;

    @Column(name = "work_card_base64", nullable = false, columnDefinition = "TEXT")
    private String workCardBase64;

    @Column(nullable = false, length = 20)
    private String status = "PENDENTE"; // PENDENTE, APROVADO, REJEITADO

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    public DocumentSubmission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRgBase64() {
        return rgBase64;
    }

    public void setRgBase64(String rgBase64) {
        this.rgBase64 = rgBase64;
    }

    public String getCpfBase64() {
        return cpfBase64;
    }

    public void setCpfBase64(String cpfBase64) {
        this.cpfBase64 = cpfBase64;
    }

    public String getWorkCardBase64() {
        return workCardBase64;
    }

    public void setWorkCardBase64(String workCardBase64) {
        this.workCardBase64 = workCardBase64;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}

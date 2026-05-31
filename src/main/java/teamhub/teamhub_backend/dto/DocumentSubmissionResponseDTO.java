package teamhub.teamhub_backend.dto;

import teamhub.teamhub_backend.model.DocumentSubmission;
import java.time.LocalDateTime;

public class DocumentSubmissionResponseDTO {
    private Integer id;
    private Integer userId;
    private String username;
    private String rgBase64;
    private String cpfBase64;
    private String workCardBase64;
    private String status;
    private LocalDateTime submittedAt;

    public DocumentSubmissionResponseDTO() {
    }

    public DocumentSubmissionResponseDTO(DocumentSubmission submission) {
        this.id = submission.getId();
        if (submission.getUser() != null) {
            this.userId = submission.getUser().getId();
            this.username = submission.getUser().getUsername();
        }
        this.rgBase64 = submission.getRgBase64();
        this.cpfBase64 = submission.getCpfBase64();
        this.workCardBase64 = submission.getWorkCardBase64();
        this.status = submission.getStatus();
        this.submittedAt = submission.getSubmittedAt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

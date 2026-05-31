package teamhub.teamhub_backend.dto;

import teamhub.teamhub_backend.model.Statement;
import java.time.LocalDateTime;

public class StatementResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorUsername;

    public StatementResponseDTO() {
    }

    public StatementResponseDTO(Statement statement) {
        this.id = statement.getId();
        this.title = statement.getTitle();
        this.content = statement.getContent();
        this.createdAt = statement.getCreatedAt();
        if (statement.getAuthor() != null) {
            this.authorUsername = statement.getAuthor().getUsername();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}

package teamhub.teamhub_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamhub.teamhub_backend.model.DocumentSubmission;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentSubmissionRepository extends JpaRepository<DocumentSubmission, Integer> {
    Optional<DocumentSubmission> findByUserId(Integer userId);
    List<DocumentSubmission> findAllByOrderBySubmittedAtDesc();
}

package teamhub.teamhub_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamhub.teamhub_backend.model.Statement;
import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Integer> {
    List<Statement> findAllByOrderByCreatedAtDesc();
}

package edu.java.repository;

import edu.java.entity.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Integer> {
    List<PollEntity> findAllByOrderByIdAsc();
}

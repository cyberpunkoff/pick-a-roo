package edu.java.repository;

import edu.java.entity.OptionEntity;
import edu.java.entity.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OptionRepository extends JpaRepository<OptionEntity, Integer> {
    List<OptionEntity> findAllByPollOrderByIdAsc(PollEntity poll);
}

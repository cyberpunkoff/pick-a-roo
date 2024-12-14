package edu.java.repository;

import edu.java.entity.OptionEntity;
import edu.java.entity.UserEntity;
import edu.java.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {
    List<VoteEntity> findByOptionAndOwner(OptionEntity option, UserEntity owner);

    List<VoteEntity> findByOption(OptionEntity option);

    List<VoteEntity> findByOwner(UserEntity owner);
}

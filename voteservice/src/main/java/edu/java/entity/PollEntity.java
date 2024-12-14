package edu.java.entity;

import edu.java.dto.PollState;
import static edu.java.dto.PollState.OPEN;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "poll")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToOne
    private UserEntity owner;
    @Enumerated(STRING)
    private PollState state = OPEN;
    @OneToMany(mappedBy = "poll")
    private Set<OptionEntity> options = new HashSet<>();

    public void addToOptions(OptionEntity optionEntity) {
        options.add(optionEntity);
        optionEntity.setPoll(this);
    }
}

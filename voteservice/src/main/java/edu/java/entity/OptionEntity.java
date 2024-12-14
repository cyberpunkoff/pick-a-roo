package edu.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "option")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String value;
    @ManyToOne
    @JoinColumn(name = "poll_id")
    private PollEntity poll;
    @OneToOne(optional = true, mappedBy = "option")
    private VoteEntity vote;

    public OptionEntity(String value) {
        this.value = value;
    }
}

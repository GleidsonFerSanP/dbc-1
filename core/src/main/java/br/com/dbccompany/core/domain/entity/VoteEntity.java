package br.com.dbccompany.core.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name="vote")
@Entity(name = "Vote")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class VoteEntity extends RootEntity {

    @ManyToOne
    @JoinColumn(name = "CODE_SCHEDULE", nullable = false)
    private ScheduleEntity schedule;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteOption option;

    @Column(nullable = false)
    private String cpf;

    @CreationTimestamp
    @Column(name = "DAT_CREATION", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Date datCreation;
}

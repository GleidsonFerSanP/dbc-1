package br.com.dbccompany.core.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name="vote")
@Entity(name = "Vote")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ToString
public class VoteEntity extends RootEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private ScheduleEntity schedule;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteOption option;

    @CreationTimestamp
    @Column(name = "DAT_CREATION", nullable = false)
    @Getter
    private Date datCreation;
}

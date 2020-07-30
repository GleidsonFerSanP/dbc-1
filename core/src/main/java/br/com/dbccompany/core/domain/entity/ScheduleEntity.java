package br.com.dbccompany.core.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Table(name="schedule")
@Entity(name = "Schedule")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ToString
public class ScheduleEntity extends RootEntity {

    public static final Integer DEFAULT_EXPIRES_TIME = 1;

    @Column(nullable = false)
    @NonNull
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @CreationTimestamp
    @Column(name = "DAT_CREATION", nullable = false)
    @Getter
    private Date datCreation;

    @UpdateTimestamp
    @Column(name = "DAT_UPDATE", nullable = false)
    @Getter
    private Date datUpdate;

    @Getter
    @Setter
    private Date expiration;

    public ScheduleEntity(final UUID scheduleCode) {
        super(scheduleCode);
    }
}

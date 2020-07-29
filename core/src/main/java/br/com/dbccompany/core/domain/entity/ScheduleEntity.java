package br.com.dbccompany.core.domain.entity;

import br.com.dbccompany.core.utils.DateUtils;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import static java.util.Objects.isNull;

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

    @Column(nullable = false)
    @Getter
    @Setter
    private Date expiration;

    @PrePersist
    private void prePersist(){
        if(isNull(this.expiration)){
            this.expiration = DateUtils.toDate(LocalDateTime.now().plus(DEFAULT_EXPIRES_TIME, ChronoUnit.MINUTES));
        }
    }
}

package br.com.dbccompany.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table(name="schedule")
@Entity(name = "Schedule")
@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
public class ScheduleEntity extends RootEntity{

    @Column(nullable = false)
    @NonNull
    private String title;
    private String description;

    @CreationTimestamp
    @Column(name = "DAT_CREATION", nullable = false)
    private Date datCreation;

    @UpdateTimestamp
    @Column(name = "DAT_UPDATE", nullable = false)
    private Date datUpdate;

    @Column(nullable = false)
    @NonNull
    private Date expiration;
}

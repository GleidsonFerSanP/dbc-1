package br.com.dbccompany.core.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@ToString
@NoArgsConstructor
public class RootEntity {

    @Id
    @GeneratedValue
    private UUID code;

    public RootEntity(final UUID code) {
        this.code = code;
    }
}

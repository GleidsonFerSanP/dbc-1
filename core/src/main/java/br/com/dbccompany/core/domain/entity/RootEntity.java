package br.com.dbccompany.core.domain.entity;

import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
public class RootEntity {

    @Id
    @Type(type = "pg-uuid")
    private UUID code;

    public RootEntity() {
        this.code = UUID.randomUUID();
    }
}

package br.com.dbccompany.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="palta")
@Entity(name = "Palta")
@EqualsAndHashCode(callSuper=false)
@Data
public class PaltaEntity extends RootEntity{

    private String title;
    private String description;

}

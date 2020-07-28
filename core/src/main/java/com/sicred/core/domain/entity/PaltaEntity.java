package com.sicred.core.domain.entity;

import lombok.EqualsAndHashCode;
import javax.persistence.Entity;

import javax.persistence.Table;

@Table(name="palta")
@Entity(name = "Palta")
@EqualsAndHashCode
public class PaltaEntity extends RootEntity{

    private String title;
    private String description;

}

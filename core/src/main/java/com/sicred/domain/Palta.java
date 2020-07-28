package com.sicred.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PALTA")
public class Palta {

    @Id
    @Column(name = "ID")
    private Long id;
}

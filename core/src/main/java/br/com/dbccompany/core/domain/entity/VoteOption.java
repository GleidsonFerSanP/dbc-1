package br.com.dbccompany.core.domain.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VoteOption {

    YES("Sim"),
    NO("No");

    private String description;
}

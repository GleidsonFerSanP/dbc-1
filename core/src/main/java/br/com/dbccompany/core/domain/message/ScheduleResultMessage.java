package br.com.dbccompany.core.domain.message;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ScheduleResultMessage implements Serializable {

    private String title;
    private String description;

    private Set<VoteResultMessage> results;
}

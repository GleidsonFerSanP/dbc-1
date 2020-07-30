package br.com.dbccompany.core.domain.entity;

import br.com.dbccompany.core.excepiton.OptionVoteInvalidException;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum VoteOption {

    YES("Sim"),
    NO("Nao");

    private String description;

    public static VoteOption from(final String text) {
        return Stream.of(VoteOption.values())
                .filter(voteOption -> voteOption.description.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new OptionVoteInvalidException("not found voteOption ".concat(text)));
    }
}

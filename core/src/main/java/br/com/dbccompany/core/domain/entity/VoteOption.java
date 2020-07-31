package br.com.dbccompany.core.domain.entity;

import br.com.dbccompany.core.exception.OptionVoteInvalidException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
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

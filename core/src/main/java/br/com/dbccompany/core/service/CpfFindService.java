package br.com.dbccompany.core.service;

import br.com.dbccompany.core.integration.client.HubDevClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CpfFindService {

    private final HubDevClient hubDevClient;

    public Boolean exists(final String cpf, final String birthday) {
        var cpfUser = hubDevClient.getCpfUser(cpf, birthday);
        return cpfUser.isStatus();
    }
}

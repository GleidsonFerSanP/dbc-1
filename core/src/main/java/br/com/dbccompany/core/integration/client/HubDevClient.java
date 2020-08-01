package br.com.dbccompany.core.integration.client;

import br.com.dbccompany.core.integration.response.CpfUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "HubDevClient", url = "${feign.client.devhub.cpf.uri}", decode404 = true)
public interface HubDevClient {

    @RequestMapping(method = RequestMethod.GET)
    CpfUserResponse getCpfUser(@RequestParam("cpf") String owner, @RequestParam("data") String repo);
}

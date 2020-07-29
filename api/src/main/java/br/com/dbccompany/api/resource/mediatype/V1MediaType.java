package br.com.dbccompany.api.resource.mediatype;

import org.springframework.http.MediaType;

public class V1MediaType extends MediaType {

    public static final String APPLICATION_VND_SICRED_APP_V_1_JSON = "application/vnd.sicred.app-v1+json";

    public V1MediaType(final String type) {
        super(type);
    }
}

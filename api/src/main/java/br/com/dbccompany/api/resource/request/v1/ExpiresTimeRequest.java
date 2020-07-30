package br.com.dbccompany.api.resource.request.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpiresTimeRequest {

    @Min(value = 1, message = "expiresTimeInMinutes must be greater than 1")
    private int expiresTimeInMinutes;
}

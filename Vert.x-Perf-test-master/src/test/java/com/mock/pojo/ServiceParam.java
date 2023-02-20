
package com.mock.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ServiceParam {

    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("type")
    private String type;
    @JsonProperty("mandatoryParams")
    private String mandatoryParams;
    @JsonProperty("conditionalMandatoryParams")
    private String conditionalMandatoryParams;
    @JsonProperty("responseCode")
    private Integer responseCode;

}

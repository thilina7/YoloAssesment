
package com.mock.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BackendConf {

    @JsonProperty("servicePort")
    private Integer servicePort;
    @JsonProperty("serviceParams")
    private List<ServiceParam> serviceParams = null;

}

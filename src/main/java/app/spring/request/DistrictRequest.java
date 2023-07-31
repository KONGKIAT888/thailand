package app.spring.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "name",
        "name_english",
        "zipcode",
        "province_id"
})
public class DistrictRequest {

    private String name;
    @JsonProperty("name_english")
    private String nameEnglish;
    private Number zipcode;
    @JsonProperty("province_id")
    private Long provinceId;

}

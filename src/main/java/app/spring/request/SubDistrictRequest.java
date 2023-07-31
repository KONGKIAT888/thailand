package app.spring.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "name",
        "name_english",
        "district_id"
})
public class SubDistrictRequest {

    private String name;
    @JsonProperty("name_english")
    private String nameEnglish;
    @JsonProperty("district_id")
    private Long districtId;

}

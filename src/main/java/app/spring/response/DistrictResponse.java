package app.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({
        "name",
        "name_english",
        "zipcode",
        "sub_districts"
})
public class DistrictResponse {

    private String name;
    @JsonProperty("name_english")
    private String nameEnglish;
    private Number zipcode;
    @JsonProperty("sub_districts")
    private List<SubDistrictResponse> subDistricts;

}

package app.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({
        "name",
        "name_english",
        "code",
        "code_english",
        "region",
        "region_english",
        "districts"
})
public class ProvinceResponse {
    private String name;
    @JsonProperty("name_english")
    private String nameEnglish;
    private String code;
    @JsonProperty("code_english")
    private String codeEnglish;
    private String region;
    @JsonProperty("region_english")
    private String regionEnglish;
    private List<DistrictResponse> districts;
}

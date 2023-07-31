package app.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "name",
        "name_english",
        "code",
        "code_english",
        "region",
        "region_english"
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
}

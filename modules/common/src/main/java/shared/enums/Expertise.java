package shared.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Expertise {

    @JsonProperty("Apprentice")
    APPRENTICE,

    @JsonProperty("Adept")
    ADEPT,

    @JsonProperty("Expert")
    EXPERT
}

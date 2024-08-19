package shared.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserRole {

    @JsonProperty("Farmer")
    ROLE_FARMER,

    @JsonProperty("Receptionist")
    ROLE_RECEPTIONIST,

    @JsonProperty("Sprayer")
    ROLE_SPRAYER;
}

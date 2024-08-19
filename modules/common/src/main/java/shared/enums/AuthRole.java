package shared.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AuthRole {

    @JsonProperty("User")
    ROLE_USER,

    @JsonProperty("Admin")
    ROLE_ADMIN;
}

package shared.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CropType {
    @JsonProperty("Fruit")
    FRUIT,

    @JsonProperty("Cereal")
    CEREAL,

    @JsonProperty("Vegetable")
    VEGETABLE;
}

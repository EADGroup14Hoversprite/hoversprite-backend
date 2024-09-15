package hoversprite.order.internal.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignSprayerRequestDto {
    private List<Long> sprayerIds;
}

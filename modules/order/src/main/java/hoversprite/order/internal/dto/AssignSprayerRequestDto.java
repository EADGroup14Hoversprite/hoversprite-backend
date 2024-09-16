package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AssignSprayerRequestDto {
    private List<Long> sprayerIds;
}

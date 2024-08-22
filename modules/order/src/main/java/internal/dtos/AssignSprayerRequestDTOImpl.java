package internal.dtos;

import lombok.Data;
import shared.dtos.order.AssignSprayerRequestDTO;

import java.util.List;

@Data
public class AssignSprayerRequestDTOImpl implements AssignSprayerRequestDTO {
    private List<Long> sprayerIds;
}

package internal.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AssignSprayerRequestDto {
    private List<Long> sprayerIds;
}

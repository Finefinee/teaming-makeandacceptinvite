package Teaming.teaming.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteRequestDTO {
    private Long managerId;
    private Long contributorId;
    private Long projectId;
}

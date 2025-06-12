package Teaming.teaming.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InviteResponseDTO {
    private Long inviteId;
    private String managerUsername;
    private String contributorUsername;
    private String projectTitle;
    private boolean accepted;
}

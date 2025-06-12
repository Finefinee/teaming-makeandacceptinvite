package Teaming.teaming.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Teaming.teaming.DTO.InviteRequestDTO;
import Teaming.teaming.Service.InviteService;

@RestController
@RequestMapping("/api/invites")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    // 초대 보내기
    @PostMapping
    public ResponseEntity<String> sendInvite(@RequestBody InviteRequestDTO request) {
        inviteService.sendInvite(request.getManagerId(), request.getContributorId(), request.getProjectId());
        return ResponseEntity.ok("초대를 성공적으로 보냈습니다.");
    }

    // 초대 수락
    @PostMapping("/{inviteId}/accept")
    public ResponseEntity<String> acceptInvite(@PathVariable Long inviteId) {
        inviteService.acceptInvite(inviteId);
        return ResponseEntity.ok("초대를 수락했습니다.");
    }
}

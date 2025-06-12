package Teaming.teaming.Service;

public interface InviteService {
    void sendInvite(Long managerId, Long normalId, Long projectId);

    void acceptInvite(Long inviteId);
}

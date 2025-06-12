package Teaming.teaming.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import Teaming.teaming.Domain.InviteDomain;
import Teaming.teaming.Domain.MemberDomain;
import Teaming.teaming.Repository.InviteRepository;
import Teaming.teaming.Repository.ProjectRepository;
import Teaming.teaming.Domain.ProjectDomain;
import Teaming.teaming.Repository.MemberRepository;

@Service
@Qualifier("InviteServiceImpl")
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;


    @Override
    @Transactional // 실패 시 롤백
    public void sendInvite(Long managerId, Long contributerId, Long projectId) {
        // managerId 없으
        MemberDomain manager = memberRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));
        // normalId 없으
        MemberDomain contributor = memberRepository.findById(contributerId)
                .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));
        // projectId 없으
        ProjectDomain project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("해당 프로젝트를 찾을 수 없습니다."));

        InviteDomain invite = new InviteDomain(manager, contributor, project);
        inviteRepository.save(invite);
    }

    @Override
    @Transactional
    public void acceptInvite(Long inviteId) {
        InviteDomain invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new RuntimeException("초대 정보를 찾을 수 없습니다."));

        if (invite.isAccepted()) {
            throw new IllegalStateException("이미 수락한 초대입니다.");
        }

        invite.setAccepted(true);

        // 1. 연관관계에 필요한 정보 꺼내기
        MemberDomain user = invite.getContributor();        // 초대된 유저 (contributer)
        ProjectDomain project = invite.getProject();     // 초대한 프로젝트

        user.addProject(project);
    }
}

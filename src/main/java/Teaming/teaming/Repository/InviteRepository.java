package Teaming.teaming.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Teaming.teaming.Domain.InviteDomain;
import Teaming.teaming.Domain.ProjectDomain;
import Teaming.teaming.Domain.MemberDomain;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<InviteDomain, Long> {

    // 특정 유저가 받은 초대 목록
    List<InviteDomain> findByContributor(MemberDomain contributor);

    // 중복 초대 여부 확인
    boolean existsByProjectAndContributor(ProjectDomain project, MemberDomain contributor);

    List<InviteDomain> findByContributorAndAcceptedTrue(MemberDomain contributor);

    // (선택) 내가 보낸 초대 목록 조회
//    List<Invite> findByMember(Member member);

    // (선택) 프로젝트 기준 초대 전체 보기
//    List<Invite> findByProject(Project project);
}

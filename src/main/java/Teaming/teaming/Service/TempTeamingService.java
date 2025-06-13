package Teaming.teaming.Service;

import Teaming.teaming.Domain.MemberDomain;
import Teaming.teaming.Domain.ProjectDomain;
import Teaming.teaming.Repository.MemberRepository;
import Teaming.teaming.Repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TempTeamingService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TempTeamingService(MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * 새로운 멤버를 생성하여 저장합니다.
     * @param username 사용자 이름
     * @param password 비밀번호
     * @param role 역할
     * @return 저장된 MemberDomain 객체
     */
    @Transactional
    public MemberDomain createMember(String username, String password, String role) {
        MemberDomain member = new MemberDomain();
        member.setUsername(username);
        member.setPassword(password);
        member.setRole(role);
        return memberRepository.save(member);
    }

    @Transactional
    public ProjectDomain createProject(String title, String description, Long leaderId) {
        MemberDomain leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new IllegalArgumentException("Leader not found with ID: " + leaderId));

        ProjectDomain project = new ProjectDomain();
        project.setTitle(title);
        project.setDescription(description);
        project.setLeader(leader); // 프로젝트 리더 설정
        project.addUser(leader);   // 리더도 프로젝트 참여자로 추가 (ManyToMany 관계)

        return projectRepository.save(project);
    }

    /**
     * 기존 프로젝트에 멤버를 추가합니다.
     * @param projectId 프로젝트 ID
     * @param memberId 추가할 멤버 ID
     * @return 업데이트된 ProjectDomain 객체
     * @throws IllegalArgumentException 프로젝트 또는 멤버를 찾을 수 없을 경우 발생
     */
    @Transactional
    public ProjectDomain addMemberToProject(Long projectId, Long memberId) {
        ProjectDomain project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));
        MemberDomain member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        project.addUser(member); // ProjectDomain의 addUser 메소드를 사용하여 양방향 관계 관리
        return projectRepository.save(project);
    }

    /**
     * 특정 멤버를 조회합니다.
     * @param memberId 멤버 ID
     * @return Optional<MemberDomain>
     */
    public Optional<MemberDomain> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 특정 프로젝트를 조회합니다.
     * @param projectId 프로젝트 ID
     * @return Optional<ProjectDomain>
     */
    public Optional<ProjectDomain> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    // 모든 멤버 조회 (테스트용)
    public List<MemberDomain> getAllMembers() {
        return memberRepository.findAll();
    }

    // 모든 프로젝트 조회 (테스트용)
    public List<ProjectDomain> getAllProjects() {
        return projectRepository.findAll();
    }
}
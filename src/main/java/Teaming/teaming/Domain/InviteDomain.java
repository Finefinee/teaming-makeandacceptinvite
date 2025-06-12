package Teaming.teaming.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class InviteDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_manager")
    private MemberDomain manager;

    @ManyToOne
    @JoinColumn(name = "project_contributer")
    private MemberDomain contributor;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectDomain project;

    @Getter
    @Setter
    private boolean accepted;

    public InviteDomain() {
        this.accepted = false;
    }

    public InviteDomain(MemberDomain manager, MemberDomain contributor, ProjectDomain project) {
        this.manager = manager;
        this.contributor = contributor;
        this.project = project;
        this.accepted = false;
    }
}

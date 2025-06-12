package Teaming.teaming.Domain;

import Teaming.teaming.Domain.MemberDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class ProjectDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private MemberDomain leader;

    @ManyToMany
    private List<MemberDomain> users = new ArrayList<>();

    public void addUser(MemberDomain user) {
        this.users.add(user);
        if (!user.getProjects().contains(this)) {
            user.getProjects().add(this);
        }
    }
}

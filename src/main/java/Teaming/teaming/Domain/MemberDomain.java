package Teaming.teaming.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table (name = "Member")
public class MemberDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String role;

    @ManyToMany(mappedBy = "users")
    private List<Teaming.teaming.Domain.ProjectDomain> projects = new ArrayList<>();

    public void addProject(Teaming.teaming.Domain.ProjectDomain project) {
        this.projects.add(project);
        if (!project.getUsers().contains(this)) {
            project.getUsers().add(this);
        }
    }
}

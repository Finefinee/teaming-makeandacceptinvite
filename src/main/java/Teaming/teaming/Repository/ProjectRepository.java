package Teaming.teaming.Repository;

import Teaming.teaming.Domain.ProjectDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Teaming.teaming.Domain.ProjectDomain;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDomain, Long> {

}

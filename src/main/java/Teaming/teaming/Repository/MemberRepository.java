package Teaming.teaming.Repository;

import Teaming.teaming.Domain.MemberDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberDomain, Long> {
    Optional<MemberDomain> findByUsername(String username);
}

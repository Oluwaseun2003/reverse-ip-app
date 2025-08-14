package repository;

import entity.ReverseIp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReverseIpRepository extends JpaRepository<ReverseIp, Long> {
}

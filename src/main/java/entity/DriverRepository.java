package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/4/27.
 */
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByUsername(String username);
    List<Driver> findByTeam(Long team);
}

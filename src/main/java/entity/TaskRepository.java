package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/4/27.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTeamOwnId(Long teamOwnId);
    List<Task> findByDriverId(Long driverId);
    List<Task> findByStatus(Long status);
}

package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/11/27.
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCid(Long cid);
}

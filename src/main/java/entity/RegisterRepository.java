package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/11/27.
 */
public interface RegisterRepository extends JpaRepository<Register, Long> {
    List<Register> findByCid(Long cid);
    List<Register> findByUid(Long uid);
    List<Register> findByRole(Long role);
}
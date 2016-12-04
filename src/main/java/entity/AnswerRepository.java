package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/11/27.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByAid(Long aid);
    List<Answer> findByUid(Long uid);
}

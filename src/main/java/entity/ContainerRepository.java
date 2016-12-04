package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/4/27.
 */
public interface ContainerRepository extends JpaRepository<Container, Long> {
    List<Container> findByCode(String code);
}

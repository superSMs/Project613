package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/11/27.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCode(String code);
    List<Course> findByTitle(String title);
    List<Course> findBySemester(String semester);
    List<Course> findByHidden(Long hidden);
}

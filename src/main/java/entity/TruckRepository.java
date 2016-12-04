package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/4/27.
 */
public interface TruckRepository extends JpaRepository<Truck, Long> {
    List<Truck> findByPlate(String plate);
    List<Truck> findByTeam(Long team);
}

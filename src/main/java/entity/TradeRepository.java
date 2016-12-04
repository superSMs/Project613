package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SMs on 2016/4/27.
 */
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findBySell(Long sell);
    List<Trade> findByBuy(Long buy);
}
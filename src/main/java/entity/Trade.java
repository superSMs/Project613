package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by SMs on 2016/4/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TRADE")

public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column
    public String dt;

    @Column
    public Long task_id;

    @Column
    public Long container_id;

    @Column
    public String container_str;

    @Column
    public Long sell;

    @Column
    public String sell_str;

    @Column
    public Long buy;

    @Column
    public String buy_str;

    @Column
    public Long price;
}

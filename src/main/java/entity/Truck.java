package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by SMs on 2016/4/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TRUCK")

public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(unique = true)
    public String plate;

    @Column
    public String model;

    @Column
    public String color;

    @Column
    public Long team;
}


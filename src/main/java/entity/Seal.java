package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by SMs on 2016/4/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "SEAL")

public class Seal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(unique = true)
    public String code;

    @Column
    public String type;

    @Column
    public Long container_id;

    @Column
    public String container_str;

    @Column
    public Long task_id;
}
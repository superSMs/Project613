package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by SMs on 2016/4/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CONTAINER")

public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(unique = true)
    public String code;

    @Column
    public String ad_load;

    @Column
    public String ad_unload;

    @Column
    public String EMS;

    @Column
    public String weight;

    @Column
    public String volume;

    @Column
    public String number;

    @Column
    public String type;

    @Column
    public String danger_lvl;

    @Column
    public Long seal_id;

    @Column
    public String seal_str;

    @Column
    public Long task_id;

}

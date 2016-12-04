package entity;

/**
 * Created by SMs on 2016/12/3.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ASSIGNMENT")

public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column
    public Long cid;

    @Column
    public String title;

    @Column
    public String desc;

    @Column
    public String due;

    @Column
    public Long score;

    @Column
    public Long published;
}

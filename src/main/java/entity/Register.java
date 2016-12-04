package entity;

/**
 * Created by SMs on 2016/12/3.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


/**
 * Created by SMs on 2016/4/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "REGISTER")

public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column
    public Long cid;

    @Column
    public Long uid;

    @Column
    public Long role;
    //0 for student, 1 for instructor
}


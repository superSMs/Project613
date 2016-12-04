package entity;

/**
 * Created by SMs on 2016/12/3.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ANSWER")

public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column
    public Long aid;

    @Column
    public Long uid;

    @Column
    public Long grade;

    @Column
    public String file;
}

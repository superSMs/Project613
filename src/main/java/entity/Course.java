package entity;

/**
 * Created by SMs on 2016/12/3.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "COURSE")

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column
    public String code;

    @Column
    public String title;

    @Column
    public String semester;

    @Column
    public String syllabus;

    @Column
    public Long hidden;
}
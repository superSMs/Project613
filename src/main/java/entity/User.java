package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long uid;

    @Column(unique = true)
    public String username;

    @Column
    public String name;

    @Column
    public String password;

    @Column
    public Long isadmin;
}

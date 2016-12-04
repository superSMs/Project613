package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by SMs on 2016/4/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TASK")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

//    @Column(unique = true)
//    public String username;

    @Column
    public String ad_from;

    @Column
    public String ad_to;

    @Column
    public Long status;

    @Column
    public String io;

    @Column
    public Long price;

    @Column
    public String dt_release;

    @Column
    public String dt_operate;

    @Column
    public String dt_load;

    @Column
    public String dt_loaded;

    @Column
    public String dt_complete;

    @Column
    public Long gps_load_id;

//    @Column
//    public int gps_load_x;
//
//    @Column
//    public int gps_load_y;

    @Column
    public Long gps_loaded_id;

//    @Column
//    public int gps_loaded_x;
//
//    @Column
//    public int gps_loaded_y;

    @Column
    public Long gps_complete_id;

//    @Column
//    public int gps_complete_x;
//
//    @Column
//    public int gps_complete_y;

    @Column
    public Long teamReleaseId;

    @Column
    public String team_release_str;

    @Column
    public Long teamOwnId;

    @Column
    public String team_own_str;

    @Column
    public Long teamOperateId;

    @Column
    public String team_operate_str;

    @Column
    public Long driverId;

    @Column
    public String driver_str;

    @Column
    public Long truckId;

    @Column
    public String truck_str;

    @Column
    public Long container_id;

    @Column
    public String container_str;

    @Column
    public Long seal_id;

    @Column
    public String seal_str;
}
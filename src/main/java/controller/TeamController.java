package controller;


import entity.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;


    /**
     * 车队登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/team/login", method = RequestMethod.POST)
    public ResponseInfo login(@RequestParam("userName") String userName,
                              @RequestParam("password") String password) throws Exception{
        ResponseInfo info = new ResponseInfo();

        List<Team> teamList = teamRepository.findByUsername(userName);
        Team currentTeam = teamList.size()>0? teamList.get(0) : null;
        if(null == currentTeam){
            info.setFailWithInfo("不存在此用户");
            return info;
        }

        if(!currentTeam.password.equals(password)){
            info.setFailWithInfo("密码错误");
            return info;
        } else {
            info.setSuccessWithInfo(String.valueOf(currentTeam.id));
            return info;
        }
    }

    /**
     * 车队注册
     * @param userName
     * @param password
     * @param name
     * @return
     */
    @RequestMapping(value = "/team/register", method = RequestMethod.POST)
    public ResponseInfo register(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 @RequestParam("name") String name) throws Exception{
        ResponseInfo info = new ResponseInfo();

        if(teamRepository.findByUsername(userName).size() > 0){
            info.setFailWithInfo("用户名已经被注册, 请换别的用户名");
            return  info;
        }

        Team team = new Team();
        team.username = userName;
        team.password = password;
        team.coin = 1000L;
        if (name != null){
            team.name = name;
        }

//        //testing testing 123
//        Driver driver = new Driver();
//        driver.name = "yeah";
//        driverRepository.save(driver);

        Team savedTeam = teamRepository.save(team);
        if ( savedTeam != null){
            info.setSuccessWithInfo(String.valueOf(savedTeam.id));
            return info;
        }else{
            info.setFailWithInfo("注册失败");
            return info;
        }

    }

    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/team/password/update", method = RequestMethod.POST)
    public ResponseInfo changePassword(@RequestParam("userId") Long userId,
                                       @RequestParam("oldPwd") String oldPwd,
                                       @RequestParam("newPwd") String newPwd) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Team team = teamRepository.findOne(userId);
        if (team == null){
            info.setFailWithInfo("不存在此用户");
        }else{
            if (!team.password.equals(oldPwd)){
                info.setFailWithInfo("旧密码不正确");
            }else {
                team.password = newPwd;
                if (teamRepository.save(team) != null) {
                    info.setSuccessWithInfo("修改密码成功");
                } else {
                    info.setFailWithInfo("修改密码失败");
                }
            }
        }

        return info;
    }

    /**
     * 修改名称
     * @param userId
     * @param oldPwd
     * @param newName
     * @return
     */
    @RequestMapping(value = "/team/name/update", method = RequestMethod.POST)
    public ResponseInfo changeName(@RequestParam("userId") Long userId,
                                       @RequestParam("oldPwd") String oldPwd,
                                       @RequestParam("newName") String newName) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Team team = teamRepository.findOne(userId);
        if (team == null){
            info.setFailWithInfo("不存在此用户");
        }else{
            if (!team.password.equals(oldPwd)){
                info.setFailWithInfo("旧密码不正确");
            }else {
                team.name = newName;
                if (teamRepository.save(team) != null) {
                    info.setSuccessWithInfo("修改名称成功");
                } else {
                    info.setFailWithInfo("修改名称失败");
                }
            }
        }

        return info;
    }

    /**
     * 获取所有车队
     * @return
     */
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public List<Team> allUser() throws Exception{
        List<Team>teams = teamRepository.findAll();
        for (int i=0; i<teams.size(); i++){
            teams.get(i).password = "";
        }
        return teams;
    }

    /**
     * 根据车队id，获取车队
     * TODO: 这里返回的其实是name这个字段  /user/{uid}/userName这里的userName其实名字取得并不好
     * @param uid
     * @return
     */
    @RequestMapping(value = "/team/{uid}/teamInfo", method = RequestMethod.GET)
    public Team fetchUserNickName(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        Team team = teamRepository.findOne(uid);
        /*if (team == null){
            info.setFailWithInfo("不存在该车队");
        }else{
            if (team.name == null || team.name.equals("")){
                info.setSuccessWithInfo("车队" + String.valueOf(team.id));
            }else {
                info.setSuccessWithInfo(team.name);
            }
        }
        */
        return team;
    }

}

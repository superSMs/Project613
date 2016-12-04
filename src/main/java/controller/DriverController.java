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
public class DriverController {
    @Autowired
    private DriverRepository driverRepository;


    /**
     * 司机登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/driver/login", method = RequestMethod.POST)
    public ResponseInfo login(@RequestParam("userName") String userName,
                              @RequestParam("password") String password) throws Exception{
        ResponseInfo info = new ResponseInfo();

        List<Driver> driverList = driverRepository.findByUsername(userName);
        Driver currentDriver = driverList.size()>0? driverList.get(0) : null;
        if(null == currentDriver){
            info.setFailWithInfo("不存在此司机");
            return info;
        }

        if(!currentDriver.password.equals(password)){
            info.setFailWithInfo("密码错误");
            return info;
        } else {
            info.setSuccessWithInfo(String.valueOf(currentDriver.id));
            return info;
        }
    }

    /**
     * 司机注册
     * @param userName
     * @param password
     * @param name
     * @param cell
     * @return
     */
    @RequestMapping(value = "/driver/register", method = RequestMethod.POST)
    public ResponseInfo register(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 @RequestParam("name") String name,
                                 @RequestParam("cell") String cell) throws Exception{
        ResponseInfo info = new ResponseInfo();

        if(driverRepository.findByUsername(userName).size() > 0){
            info.setFailWithInfo("用户名已经被注册, 请换别的用户名");
            return  info;
        }

        Driver driver = new Driver();
        driver.username = userName;
        driver.password = password;
        driver.name = name;
        driver.cell = cell;

        Driver savedDriver = driverRepository.save(driver);
        if ( savedDriver != null){
            info.setSuccessWithInfo(String.valueOf(savedDriver.id));
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
    @RequestMapping(value = "/driver/password/update", method = RequestMethod.POST)
    public ResponseInfo changePassword(@RequestParam("userId") Long userId,
                                       @RequestParam("oldPwd") String oldPwd,
                                       @RequestParam("newPwd") String newPwd) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Driver driver = driverRepository.findOne(userId);
        if (driver == null){
            info.setFailWithInfo("不存在此用户");
        }else{
            if (!driver.password.equals(oldPwd)){
                info.setFailWithInfo("旧密码不正确");
            }else {
                driver.password = newPwd;
                if (driverRepository.save(driver) != null) {
                    info.setSuccessWithInfo("修改密码成功");
                } else {
                    info.setFailWithInfo("修改密码失败");
                }
            }
        }

        return info;
    }

    /**
     * 修改信息
     * @param userId
     * @param oldPwd
     * @param newName
     * @param newCell
     * @return
     */
    @RequestMapping(value = "/driver/info/update", method = RequestMethod.POST)
    public ResponseInfo changeName(@RequestParam("userId") Long userId,
                                   @RequestParam("oldPwd") String oldPwd,
                                   @RequestParam(value="newName", required = false) String newName,
                                   @RequestParam(value="newCell", required = false) String newCell)
            throws Exception{
        ResponseInfo info = new ResponseInfo();

        Driver driver = driverRepository.findOne(userId);
        if (driver == null){
            info.setFailWithInfo("不存在此用户");
        }else{
            if (!driver.password.equals(oldPwd)){
                info.setFailWithInfo("旧密码不正确");
            }else {
                if (newName != null){
                    driver.name = newName;
                }
                if (newCell != null){
                    driver.cell = newCell;
                }

                if (driverRepository.save(driver) != null) {
                    info.setSuccessWithInfo("修改名字成功");
                } else {
                    info.setFailWithInfo("修改名字失败");
                }
            }
        }

        return info;
    }

    /**
     * 接受司机
     * @param driverId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/driver/accept", method = RequestMethod.POST)
    public ResponseInfo accept(@RequestParam("driverId") Long driverId,
                               @RequestParam("teamId") Long teamId)throws Exception{
        ResponseInfo info = new ResponseInfo();

        Driver driver = driverRepository.findOne(driverId);
        if (driver == null){
            info.setFailWithInfo("不存在此用户");
        }else{
            driver.team = teamId;

            if (driverRepository.save(driver) != null) {
                info.setSuccessWithInfo("接受司机成功");
            } else {
                info.setFailWithInfo("接受司机失败");
            }

        }

        return info;
    }

    /**
     * 解雇司机
     * @param driverId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/driver/fire", method = RequestMethod.POST)
    public ResponseInfo fire(@RequestParam("driverId") Long driverId,
                               @RequestParam("teamId") Long teamId)throws Exception{
        ResponseInfo info = new ResponseInfo();

        Driver driver = driverRepository.findOne(driverId);
        if (driver == null){
            info.setFailWithInfo("不存在此用户");
        }else if(driver.team != teamId){
            info.setFailWithInfo("只能解雇本队司机");
        }else{
            driver.team = null;

            if (driverRepository.save(driver) != null) {
                info.setSuccessWithInfo("解雇司机成功");
            } else {
                info.setFailWithInfo("解雇司机失败");
            }
        }

        return info;
    }

    /**
     * 获取所有司机
     * @return
     */
    @RequestMapping(value = "/drivers", method = RequestMethod.GET)
    public List<Driver> allUser() throws Exception{
        List<Driver>drivers = driverRepository.findAll();
        for (int i=0; i<drivers.size(); i++){
            drivers.get(i).password = "";
        }
        return drivers;
    }

    /**
     * 获取车队司机
     * @param uid
     * @return
     */
    @RequestMapping(value = "/drivers/{uid}/team", method = RequestMethod.GET)
    public List<Driver> teamUser(@PathVariable Long uid) throws Exception{
        List<Driver>drivers = driverRepository.findByTeam(uid);
        for (int i=0; i<drivers.size(); i++){
            drivers.get(i).password = "";
        }
        return drivers;
    }

    /**
     * 根据司机id，获取司机信息
     * TODO: 这里返回的其实是name这个字段  /user/{uid}/userName这里的userName其实名字取得并不好
     * @param uid
     * @return
     */
    @RequestMapping(value = "/driver/{uid}/driverInfo", method = RequestMethod.GET)
    public Driver fetchDriverInfo(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        Driver driver = driverRepository.findOne(uid);
        /*
        if (driver == null){
            info.setFailWithInfo("不存在该司机");
        }else{
            if (driver.name == null || driver.name.equals("")){
                info.setSuccessWithInfo(driver.cell + " " + "司机" + String.valueOf(driver.id));
            }else {
                info.setSuccessWithInfo(driver.cell + " " + driver.name);
            }
        }
        */
        return driver;
    }
}

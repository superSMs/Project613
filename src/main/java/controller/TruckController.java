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
public class TruckController {
    @Autowired
    private TruckRepository truckRepository;

    /**
     * 注册卡车
     * @param plate
     * @param teamId
     * @param model
     * @param color
     * @return
     */
    @RequestMapping(value = "/truck/new", method = RequestMethod.POST)
    public ResponseInfo register(@RequestParam("plate") String plate,
                                 @RequestParam("teamId") Long teamId,
                                 @RequestParam(value="model", required = false) String model,
                                 @RequestParam(value="color", required = false) String color)
            throws Exception{
        ResponseInfo info = new ResponseInfo();

        if(truckRepository.findByPlate(plate).size() > 0){
            info.setFailWithInfo("车牌已经被注册");
            return  info;
        }

        Truck truck = new Truck();
        truck.plate = plate;
        truck.team = teamId;
        if (model != null){
            truck.model = model;
        }
        if (color != null){
            truck.color = color;
        }

        Truck savedTruck = truckRepository.save(truck);
        if ( savedTruck != null){
            info.setSuccessWithInfo(String.valueOf(savedTruck.id));
            return info;
        }else{
            info.setFailWithInfo("注册失败");
            return info;
        }
    }

    /**
     * 删除卡车
     * @param truckId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/truck/del", method = RequestMethod.POST)
    public ResponseInfo delete(@RequestParam("truckId") Long truckId,
                              @RequestParam("teamId") Long teamId) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Truck truck = truckRepository.findOne(truckId);
        if (truck == null){
            info.setFailWithInfo("不存在此卡车");
        }else if(truck.team != teamId){
            info.setFailWithInfo("只能解雇本队卡车");
        }else{
            truck.team = null;

            if (truckRepository.save(truck) != null) {
                info.setSuccessWithInfo("删除卡车成功");
            } else {
                info.setFailWithInfo("删除卡车失败");
            }
        }

        return info;
    }

    /**
     * 修改信息
     * @param truckId
     * @param teamId
     * @param model
     * @param color
     * @return
     */
    @RequestMapping(value = "/truck/info/update", method = RequestMethod.POST)
    public ResponseInfo changeInfo(@RequestParam("truckId") Long truckId,
                                   @RequestParam("teamId") Long teamId,
                                   @RequestParam(value="model", required = false) String model,
                                   @RequestParam(value="color", required = false) String color)
            throws Exception{
        ResponseInfo info = new ResponseInfo();

        Truck truck = truckRepository.findOne(truckId);
        if (truck == null){
            info.setFailWithInfo("不存在此卡车");
        }else if(truck.team != teamId){
            info.setFailWithInfo("只能修改本队卡车");
        }else{
            truck.model = model;
            truck.color = color;
            if (truckRepository.save(truck) != null) {
                info.setSuccessWithInfo("修改信息成功");
            } else {
                info.setFailWithInfo("修改信息失败");
            }
        }

        return info;
    }

    /**
     * 获取所有卡车
     * @return
     */
    @RequestMapping(value = "/trucks", method = RequestMethod.GET)
    public List<Truck> allTrucks() throws Exception{
        List<Truck>trucks = truckRepository.findAll();
        /*
        for (int i=0; i<drivers.size(); i++){
            trucks.get(i).password = "";
        }
        */
        return trucks;
    }

    /**
     * 获取车队卡车
     * @param uid
     * @return
     */
    @RequestMapping(value = "/trucks/{uid}/team", method = RequestMethod.GET)
    public List<Truck> teamTrucks(@PathVariable Long uid) throws Exception{
        List<Truck>trucks = truckRepository.findByTeam(uid);
        /*
        for (int i=0; i<drivers.size(); i++){
            drivers.get(i).password = "";
        }
        */
        return trucks;
    }

    /**
     * 根据卡车id，获取卡车信息
     * TODO: 这里返回的其实是name这个字段  /user/{uid}/userName这里的userName其实名字取得并不好
     * @param uid
     * @return
     */
    @RequestMapping(value = "/truck/{uid}/truckInfo", method = RequestMethod.GET)
    public Truck fetchTruckInfo(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        Truck truck = truckRepository.findOne(uid);
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
        return truck;
    }
}

package controller;


import entity.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
//import java.lang.Integer;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Date;
@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private SealRepository sealRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private GPSRepository gpsRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TruckRepository truckRepository;
    /**
     * 新建任务
     * @param teamId
     * @param from
     * @param to
     * @param io
     * @param containerCode
     * @param sealCode
     * @param EMS
     * @param weight
     * @param volume
     * @param number
     * @param containerType
     * @param sealType
     * @param danger_lvl
     * @return
     */
    @RequestMapping(value = "/task/new", method = RequestMethod.POST)
    public ResponseInfo tasknew(@RequestParam("teamId") Long teamId,
                                @RequestParam(value="from", required = false) String from,
                                @RequestParam(value="to", required = false) String to,
                                @RequestParam(value="io", required = false) String io,
                                @RequestParam(value="containerCode", required = false) String containerCode,
                                @RequestParam(value="sealCode", required = false) String sealCode,
                                @RequestParam(value="EMS", required = false) String EMS,
                                @RequestParam(value="weight", required = false) String weight,
                                @RequestParam(value="volume", required = false) String volume,
                                @RequestParam(value="number", required = false) String number,
                                @RequestParam(value="containerType", required = false) String containerType,
                                @RequestParam(value="sealType", required = false) String sealType,
                                @RequestParam(value="danger_lvl", required = false) String danger_lvl) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Team team = teamRepository.findOne(teamId);

        Task task = new Task();
        Container container = new Container();
        Seal seal = new Seal();

        task.status = 0L;

        //task properties
        if (from != null) {
            task.ad_from = from;
            container.ad_load = from;
        }

        if (to != null) {
            task.ad_to = to;
            container.ad_unload = to;
        }

        if (io != null)
            task.io = io;

        task.teamReleaseId = team.id;
        task.team_release_str = team.name;
        task.teamOwnId = team.id;
        task.team_own_str = team.name;

        //container properties
        if (containerCode != null){
            if(containerRepository.findByCode(containerCode).size() > 0){
                info.setFailWithInfo("装箱号已经被注册");
                return  info;
            }
            container.code = containerCode;
        }


        if (EMS != null)
            container.EMS = EMS;

        if (weight != null)
            container.weight = weight;

        if (volume != null)
            container.volume = volume;

        if (number != null)
            container.number = number;

        if (containerType != null)
            container.type = containerType;

        if (danger_lvl != null)
            container.danger_lvl = danger_lvl;

        //seal properties
        if (sealCode != null) {
            if(sealRepository.findByCode(sealCode).size() > 0){
                info.setFailWithInfo("封箱号已经被注册");
                return  info;
            }
            seal.code = sealCode;
        }
        if (sealType != null)
            seal.type = sealType;

        Task savedTask = taskRepository.save(task);
        Container savedContainer = containerRepository.save(container);
        Seal savedSeal = sealRepository.save(seal);

        //...
        savedTask.container_id = savedContainer.id;
        savedTask.container_str = savedContainer.code;
        savedTask.seal_id = savedSeal.id;
        savedTask.seal_str = savedSeal.code;

        savedContainer.task_id = savedTask.id;
        savedContainer.seal_id = savedSeal.id;
        savedContainer.seal_str = savedSeal.code;

        savedSeal.task_id = savedTask.id;
        savedSeal.container_id = savedContainer.id;
        savedSeal.container_str = savedContainer.code;

        Task savedTask2 = taskRepository.save(savedTask);
        Container savedContainer2 = containerRepository.save(savedContainer);
        Seal savedSeal2 = sealRepository.save(savedSeal);

        if ( savedTask2 != null &&
            savedContainer2 != null &&
            savedSeal2 != null){
            info.setSuccessWithInfo(String.valueOf(savedTask.id));
            return info;
        }else{
            info.setFailWithInfo("注册失败");
            return info;
        }

    }

    /**
     * 编辑任务
     * @param teamId
     * @param taskId
     * @param from
     * @param to
     * @param io
     * @param containerCode
     * @param sealCode
     * @param EMS
     * @param weight
     * @param volume
     * @param number
     * @param containerType
     * @param sealType
     * @param danger_lvl
     * @return
     */
    @RequestMapping(value = "/task/edit", method = RequestMethod.POST)
    public ResponseInfo taskedit(@RequestParam("teamId") Long teamId,
                                @RequestParam("taskId") Long taskId,
                                @RequestParam(value="from", required = false) String from,
                                @RequestParam(value="to", required = false) String to,
                                @RequestParam(value="io", required = false) String io,
                                @RequestParam(value="containerCode", required = false) String containerCode,
                                @RequestParam(value="sealCode", required = false) String sealCode,
                                @RequestParam(value="EMS", required = false) String EMS,
                                @RequestParam(value="weight", required = false) String weight,
                                @RequestParam(value="volume", required = false) String volume,
                                @RequestParam(value="number", required = false) String number,
                                @RequestParam(value="containerType", required = false) String containerType,
                                @RequestParam(value="sealType", required = false) String sealType,
                                @RequestParam(value="danger_lvl", required = false) String danger_lvl) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Team team = teamRepository.findOne(teamId);
        Task task = taskRepository.findOne(taskId);
        Container container = containerRepository.findOne(task.container_id);
        Seal seal = sealRepository.findOne(task.seal_id);

        task.status = 0L;

        //task properties
        if (from != null) {
            task.ad_from = from;
            container.ad_load = from;
        }

        if (to != null) {
            task.ad_to = to;
            container.ad_unload = to;
        }

        if (io != null)
            task.io = io;

        //container properties
        if (containerCode != null) {
            List<Container>containers = containerRepository.findByCode(containerCode);
            if(containers.size() > 0 &&
               containers.get(0).id != task.container_id){
                info.setFailWithInfo("装箱号已经被注册");
                return  info;
            }

            container.code = containerCode;
            task.container_str = containerCode;
        }
        if (EMS != null)
            container.EMS = EMS;

        if (weight != null)
            container.weight = weight;

        if (volume != null)
            container.volume = volume;

        if (number != null)
            container.number = number;

        if (containerType != null)
            container.type = containerType;

        if (danger_lvl != null)
            container.danger_lvl = danger_lvl;

        //seal properties
        if (sealCode != null) {
            List<Seal>seals = sealRepository.findByCode(sealCode);
            if(seals.size() > 0 &&
                seals.get(0).id != task.seal_id){
                info.setFailWithInfo("封箱号已经被注册");
                return  info;
            }

            seal.code = sealCode;
            task.seal_str = sealCode;
        }

        if (sealType != null)
            seal.type = sealType;

        Task savedTask = taskRepository.save(task);
        Container savedContainer = containerRepository.save(container);
        Seal savedSeal = sealRepository.save(seal);

        if ( savedTask != null &&
                savedContainer != null &&
                savedSeal != null){
            info.setSuccessWithInfo(String.valueOf(savedTask.id));
            return info;
        }else{
            info.setFailWithInfo("编辑失败");
            return info;
        }

    }

    /**
     * 发布任务
     * @param taskId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/task/release", method = RequestMethod.POST)
    public ResponseInfo release(@RequestParam("taskId") Long taskId,
                                @RequestParam("teamId") Long teamId) throws Exception{
        ResponseInfo info = new ResponseInfo();
        //testing testing 123
        Team team = teamRepository.findOne(teamId);
        Task task = taskRepository.findOne(taskId);

//        Team team = new Team();
//        Task task = new Task();
        Container container = containerRepository.findOne(task.container_id);
        Seal seal = sealRepository.findOne(task.seal_id);

        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(task.teamReleaseId != teamId){
            info.setFailWithInfo("只能发布本队任务");
        }else if(task.ad_from == null ||
                task.ad_to == null ||
                task.io == null ||
                container.code == null ||
                container.ad_load == null ||
                container.ad_unload == null ||
                container.EMS == null)/*
        else if(task.ad_from == "" ||
                task.ad_to == "" ||
                task.io == "" ||
                container.code == "" ||
                container.ad_load == "" ||
                container.ad_unload == "" ||
                container.EMS == "")*/{

            //as all tasks
            info.setFailWithInfo("任务信息不全");
        }else{
            //import, team should provide ALL info
            if(task.io == "1" && (container.weight == null ||
                    container.volume == null ||
                    container.number == null ||
                    container.type == null ||
                    container.danger_lvl == null ||
                    seal.code == null ||
                    seal.type == null))/*
            (container.weight == "" ||
                    container.volume == "" ||
                    container.number == "" ||
                    container.type == "" ||
                    container.danger_lvl == "" ||
                    seal.code == "" ||
                    seal.type == ""))*/{

                //as import tasks
                info.setFailWithInfo("任务信息不全");
            }

            // Instantiate a Date object
            Date date = new Date();
            // display time and date using toString()
            task.dt_release = date.toString();
            task.status = 1L;
            if (taskRepository.save(task) != null) {
                info.setSuccessWithInfo("发布任务成功");
            } else {
                info.setFailWithInfo("发布任务失败");
            }
        }
        return info;
    }

    /**
     * 分配任务
     * @param taskId
     * @param driverId
     * @param truckId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/task/assign", method = RequestMethod.POST)
    public ResponseInfo assign(@RequestParam("taskId") Long taskId,
                               @RequestParam("driverId") Long driverId,
                               @RequestParam("truckId") Long truckId,
                               @RequestParam("teamId") Long teamId) throws Exception{
        ResponseInfo info = new ResponseInfo();

        //Team team = teamRepository.findOne(teamId);
        Task task = taskRepository.findOne(taskId);
        Team team = teamRepository.findOne(teamId);
        Driver driver = driverRepository.findOne(driverId);
        Truck truck = truckRepository.findOne(truckId);

        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(task.teamOwnId != teamId){
            info.setFailWithInfo("只能分配本队任务");
        }else{
            task.teamOperateId = teamId;
            task.team_operate_str = team.name;

            task.driverId = driverId;
            task.driver_str = driver.name;

            task.truckId = truckId;
            task.truck_str = truck.plate;

            // Instantiate a Date object
            Date date = new Date();
            // display time and date using toString()
            task.dt_operate = date.toString();
            task.status = 3L;

            if (taskRepository.save(task) != null) {
                info.setSuccessWithInfo("分配任务成功");
            } else {
                info.setFailWithInfo("分配任务失败");
            }
        }
        return info;
    }

    /**
     * 出售任务
     * @param taskId
     * @param teamId
     * @param price
     * @return
     */
    @RequestMapping(value = "/task/sell", method = RequestMethod.POST)
    public ResponseInfo sell(@RequestParam("taskId") Long taskId,
                             @RequestParam("teamId") Long teamId,
                             @RequestParam("price") Long price) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Team team = teamRepository.findOne(teamId);
        Task task = taskRepository.findOne(taskId);
        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(task.teamOwnId != teamId){
            info.setFailWithInfo("只能出售本队任务");
        }else if(task.status != 1L){
            info.setFailWithInfo("任务不可出售");
        }else{
            task.price = price;
            //task.dt_operate = DateTime.Now.ToString();
            task.status = 2L;

            if (taskRepository.save(task) != null) {
                info.setSuccessWithInfo("出售任务成功");
            } else {
                info.setFailWithInfo("出售任务失败");
            }
        }

        return info;
    }

    /**
     * 撤回任务
     * @param taskId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/task/unsell", method = RequestMethod.POST)
    public ResponseInfo unsell(@RequestParam("taskId") Long taskId,
                             @RequestParam("teamId") Long teamId) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Team team = teamRepository.findOne(teamId);
        Task task = taskRepository.findOne(taskId);
        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(task.teamOwnId != teamId){
            info.setFailWithInfo("只能撤回本队任务");
        }else{
            task.price = 0L;
            //task.dt_operate = DateTime.Now.ToString();
            if(task.status != 2L)
                info.setFailWithInfo("任务未出售");

            task.status = 1L;

            if (taskRepository.save(task) != null) {
                info.setSuccessWithInfo("撤回任务成功");
            } else {
                info.setFailWithInfo("撤回任务失败");
            }
        }

        return info;
    }

    /**
     * 购买任务
     * @param taskId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/task/buy", method = RequestMethod.POST)
    public ResponseInfo buy(@RequestParam("taskId") Long taskId,
                            @RequestParam("teamId") Long teamId) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Task task = taskRepository.findOne(taskId);
        Team buy = teamRepository.findOne(teamId);
        Team sell = teamRepository.findOne(task.teamOwnId);
        //Container container = containerRepository.findOne(task.container_id);
        Trade trade = new Trade();

        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(buy.coin < task.price){
            info.setFailWithInfo("余额不足");
        }else if(teamId == sell.id){
            info.setFailWithInfo("不可购买本队任务");
        }else{
            // Instantiate a Date object
            Date date = new Date();
            // display time and date using toString()
            trade.dt = date.toString();

            trade.task_id = task.id;
            trade.container_id = task.container_id;
            trade.container_str = task.container_str;

            trade.sell = sell.id;
            trade.sell_str = sell.name;

            trade.buy = buy.id;
            trade.buy_str = buy.name;

            trade.price = task.price;

            task.teamOwnId = buy.id;
            task.team_own_str = buy.name;

            task.status = 1L;

            buy.coin = buy.coin - task.price;
            sell.coin = sell.coin + task.price;
            task.price = 0L;

            if (taskRepository.save(task) != null &&
                tradeRepository.save(trade) != null &&
                teamRepository.save(buy) != null &&
                teamRepository.save(sell)!= null) {
                info.setSuccessWithInfo("购买任务成功");
            } else {
                info.setFailWithInfo("购买任务失败");
            }

        }

        return info;
    }

    /**
     * 开始提货
     * @param taskId
     * @param driverId
     * @param x
     * @param y
     * @return
     */
    @RequestMapping(value = "/task/load", method = RequestMethod.POST)
    public ResponseInfo load(@RequestParam("taskId") Long taskId,
                             @RequestParam("driverId") Long driverId,
                             @RequestParam("x") Long x,
                             @RequestParam("y") Long y) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Task task = taskRepository.findOne(taskId);
        //Driver driver = driverRepository.findOne(driverId);
        GPS gps = new GPS();

        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(task.driverId != driverId){
            info.setFailWithInfo("只能操作本人任务");
        }else{
            gps.x = x;
            gps.y = y;
            GPS savedGPS = gpsRepository.save(gps);

            task.gps_load_id = savedGPS.id;
//            task.gps_load_x = x;
//            task.gps_load_y = y;
            // Instantiate a Date object
            Date date = new Date();
            // display time and date using toString()
            task.dt_load = date.toString();

            if(task.status != 3L)
                info.setFailWithInfo("任务步骤错误");

            task.status = 4L;

            if (taskRepository.save(task) != null &&
                gpsRepository.save(savedGPS) != null) {
                info.setSuccessWithInfo("状态更新成功-开始提货");
            } else {
                info.setFailWithInfo("状态更新失败-开始提货");
            }
        }
        return info;
    }

    /**
     * 完成提货
     * @param taskId
     * @param driverId
     * @param x
     * @param y
     * @param sealCode
     * @param weight
     * @param volume
     * @param number
     * @param containerType
     * @param sealType
     * @param danger_lvl
     * @return
     */
    @RequestMapping(value = "/task/loaded", method = RequestMethod.POST)
    public ResponseInfo loaded(@RequestParam("taskId") Long taskId,
                               @RequestParam("driverId") Long driverId,
                               @RequestParam("x") Long x,
                               @RequestParam("y") Long y,
                               @RequestParam(value="sealCode", required = false) String sealCode,
                               @RequestParam(value="weight", required = false) String weight,
                               @RequestParam(value="volume", required = false) String volume,
                               @RequestParam(value="number", required = false) String number,
                               @RequestParam(value="containerType", required = false) String containerType,
                               @RequestParam(value="sealType", required = false) String sealType,
                               @RequestParam(value="danger_lvl", required = false) String danger_lvl) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Task task = taskRepository.findOne(taskId);
        Container container = containerRepository.findOne(task.container_id);
        Seal seal = sealRepository.findOne(task.seal_id);
        //Driver driver = driverRepository.findOne(driverId);
        GPS gps = new GPS();

        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(task.driverId != driverId){
            info.setFailWithInfo("只能操作本人任务");
        }else{
            if(task.io == "2"){
                if (sealCode == null ||
                    weight == null ||
                    volume == null ||
                    number == null ||
                    containerType == null ||
                    sealType == null ||
                    danger_lvl == null){
                    info.setFailWithInfo("任务信息不全");
                }else{
                    if(sealRepository.findByCode(sealCode).size() > 0){
                        info.setFailWithInfo("铅封号已经被注册");
                        return  info;
                    }
                    seal.code = sealCode;
                    seal.type = sealType;

                    container.weight = weight;
                    container.volume = volume;
                    container.number = number;
                    container.type = containerType;
                    container.danger_lvl = danger_lvl;
                }
            }

            gps.x = x;
            gps.y = y;
            GPS savedGPS = gpsRepository.save(gps);

            task.gps_loaded_id = savedGPS.id;
            // Instantiate a Date object
            Date date = new Date();
            // display time and date using toString()
            task.dt_loaded = date.toString();

//            task.gps_loaded_id = gps.id;
//            task.gps_loaded_x = x;
//            task.gps_loaded_y = y;

            if(task.status != 4L)
                info.setFailWithInfo("任务步骤错误");

            task.status = 5L;

            if (taskRepository.save(task) != null &&
                    gpsRepository.save(savedGPS) != null) {
                info.setSuccessWithInfo("状态更新成功-完成提货");
            } else {
                info.setFailWithInfo("状态更新失败-完成提货");
            }
        }
        return info;
    }

    /**
     * 完成任务
     * @param taskId
     * @param driverId
     * @param x
     * @param y
     * @return
     */
    @RequestMapping(value = "/task/complete", method = RequestMethod.POST)
    public ResponseInfo complete(@RequestParam("taskId") Long taskId,
                                 @RequestParam("driverId") Long driverId,
                                 @RequestParam("x") Long x,
                                 @RequestParam("y") Long y) throws Exception{
        ResponseInfo info = new ResponseInfo();

        Task task = taskRepository.findOne(taskId);
        //Driver driver = driverRepository.findOne(driverId);
        GPS gps = new GPS();

        if (task == null){
            info.setFailWithInfo("不存在此任务");
        }else if(task.driverId != driverId){
            info.setFailWithInfo("只能操作本人任务");
        }else{
            gps.x = x;
            gps.y = y;
            GPS savedGPS = gpsRepository.save(gps);

            task.gps_complete_id = savedGPS.id;

            // Instantiate a Date object
            Date date = new Date();
            // display time and date using toString()
            task.dt_complete = date.toString();

//            task.gps_complete_id = gps.id;
//            task.gps_complete_x = gps.x;
//            task.gps_complete_y = gps.y;

            if(task.status != 5L)
                info.setFailWithInfo("任务步骤错误");

            task.status = 6L;

            if (taskRepository.save(task) != null &&
                    gpsRepository.save(savedGPS) != null) {
                info.setSuccessWithInfo("状态更新成功-完成任务");
            } else {
                info.setFailWithInfo("状态更新失败-完成任务");
            }
        }
        return info;
    }

    /**
     * 获取车队当前任务
     * @param uid
     * @return
     */
    @RequestMapping(value = "/tasks/{uid}/todo", method = RequestMethod.GET)
    public List<Task> teamTodoTask(@PathVariable Long uid) throws Exception{
        List<Task>tasks = taskRepository.findByTeamOwnId(uid);
        List<Trade>trades = tradeRepository.findBySell(uid);
        List<Task>process = new ArrayList();

        for (int i=0; i<tasks.size(); i++){
            Task task = tasks.get(i);
            if(task.status != 6L)
                process.add(task);
        }

        for (int i=0; i<trades.size(); i++){
            Task task = taskRepository.findOne(trades.get(i).task_id);
            if(task.status != 6L)
                process.add(task);
        }
        List<Task> res = new ArrayList<Task>(new HashSet<Task>(process));
        return res;
    }

    /**
     * 获取车队历史任务
     * @param uid
     * @return
     */
    @RequestMapping(value = "/tasks/{uid}/done", method = RequestMethod.GET)
    public List<Task> teamDoneTask(@PathVariable Long uid) throws Exception{
        List<Task>tasks = taskRepository.findByTeamOwnId(uid);
        List<Trade>trades = tradeRepository.findBySell(uid);
        List<Task>process = new ArrayList();

        for (int i=0; i<tasks.size(); i++){
            Task task = tasks.get(i);
            if(task.status == 6L)
                process.add(task);
        }
        for (int i=0; i<trades.size(); i++){
            Task task = taskRepository.findOne(trades.get(i).task_id);
            if(task.status == 6L)
                process.add(task);
        }
        List<Task> res = new ArrayList<Task>(new HashSet<Task>(process));
        return res;
    }

    /**
     * 搜索任务
     * @param uid
     * @return
     */
    @RequestMapping(value = "/tasks/{uid}/search", method = RequestMethod.GET)
    public List<Task> containerId(@PathVariable String uid) throws Exception{
        List<Container>containers = containerRepository.findByCode(uid);
        List<Task>res = null;

        for (int i=0; i<containers.size(); i++){
            Container container = containers.get(i);
            Task task = taskRepository.findOne(container.task_id);
            if(task.status != null)
                res.add(task);
        }
        //List<Task> res = new ArrayList<Task>(new HashSet<Task>(tasks));
        return res;
    }

    /**
     * 获取司机任务
     * @param uid
     * @return
     */
    @RequestMapping(value = "/tasks/{uid}/driver", method = RequestMethod.GET)
    public List<Task> driverTask(@PathVariable Long uid) throws Exception{
        List<Task>tasks = taskRepository.findByDriverId(uid);

        return tasks;
    }

    /**
     * 获取市场任务
     * @return
     */
    @RequestMapping(value = "/tasks/market", method = RequestMethod.GET)
    public List<Task> marketTask() throws Exception{
        List<Task>tasks = taskRepository.findByStatus(2L);
        /*
        for (int i=0; i<tasks.size(); i++){

            tasks.get(i).password = "";
        }
        */
        return tasks;
    }

    /**
     * 获取出售历史
     * @param uid
     * @return
     */
    @RequestMapping(value = "/trades/{uid}/sell", method = RequestMethod.GET)
    public List<Trade> sellRec(@PathVariable Long uid) throws Exception{
        List<Trade>trades = tradeRepository.findBySell(uid);

        return trades;
    }

    /**
     * 获取购买历史
     * @param uid
     * @return
     */
    @RequestMapping(value = "/trades/{uid}/buy", method = RequestMethod.GET)
    public List<Trade> buyRec(@PathVariable Long uid) throws Exception{
        List<Trade>trades = tradeRepository.findByBuy(uid);

        return trades;
    }

    /**
     * 根据装箱号code，获取装箱单
     * @param uid
     * @return
     */
    @RequestMapping(value = "/container/{uid}/code", method = RequestMethod.GET)
    public Container searchContainer(@PathVariable String uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();
        List<Container> containers = containerRepository.findByCode(uid);
        Container container = containers.size()>0? containers.get(0) : null;
        return container;

    }

    /**
     * 根据封箱号code，获取封箱单
     * @param uid
     * @return
     */
    @RequestMapping(value = "/seal/{uid}/code", method = RequestMethod.GET)
    public Seal searchSeal(@PathVariable String uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        List<Seal> seals = sealRepository.findByCode(uid);
        Seal seal = seals.size()>0? seals.get(0) : null;
        return seal;

    }

    /**
     * 根据任务id，获取任务信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/task/{uid}/taskInfo", method = RequestMethod.GET)
    public Task fetchTaskInfo(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        Task task = taskRepository.findOne(uid);
        /*
        if (task == null){

            info.setFailWithInfo("不存在该任务");
        }else{
            if (task.name == null || task.name.equals("")){
                info.setSuccessWithInfo("用户" + String.valueOf(task.id));
            }else {
                info.setSuccessWithInfo(team.name);
            }
        }
        */
        return task;
    }

    /**
     * 根据装箱号id，获取装箱单
     * @param uid
     * @return
     */
    @RequestMapping(value = "/container/{uid}/containerInfo", method = RequestMethod.GET)
    public Container fetchContainerInfo(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        Container container = containerRepository.findOne(uid);
        /*
        if (task == null){

            info.setFailWithInfo("不存在该任务");
        }else{
            if (task.name == null || task.name.equals("")){
                info.setSuccessWithInfo("用户" + String.valueOf(task.id));
            }else {
                info.setSuccessWithInfo(team.name);
            }
        }
        */
        return container;
    }

    /**
     * 根据封箱号id，获取装封单
     * @param uid
     * @return
     */
    @RequestMapping(value = "/seal/{uid}/sealInfo", method = RequestMethod.GET)
    public Seal fetchSealInfo(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        Seal seal = sealRepository.findOne(uid);
        /*
        if (task == null){

            info.setFailWithInfo("不存在该任务");
        }else{
            if (task.name == null || task.name.equals("")){
                info.setSuccessWithInfo("用户" + String.valueOf(task.id));
            }else {
                info.setSuccessWithInfo(team.name);
            }
        }
        */
        return seal;
    }

    /**
     * 根据交易id，获取交易信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/trade/{uid}/tradeInfo", method = RequestMethod.GET)
    public Trade fetchTradeInfo(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        Trade trade = tradeRepository.findOne(uid);
        /*
        if (task == null){

            info.setFailWithInfo("不存在该任务");
        }else{
            if (task.name == null || task.name.equals("")){
                info.setSuccessWithInfo("用户" + String.valueOf(task.id));
            }else {
                info.setSuccessWithInfo(team.name);
            }
        }
        */
        return trade;
    }

    /**
     * 根GPSid，获取GPS信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/gps/{uid}/gpsInfo", method = RequestMethod.GET)
    public GPS fetchGPSInfo(@PathVariable Long uid) throws Exception{
        //ResponseInfo info = new ResponseInfo();

        GPS gps = gpsRepository.findOne(uid);
        /*
        if (task == null){

            info.setFailWithInfo("不存在该任务");
        }else{
            if (task.name == null || task.name.equals("")){
                info.setSuccessWithInfo("用户" + String.valueOf(task.id));
            }else {
                info.setSuccessWithInfo(team.name);
            }
        }
        */
        return gps;
    }

}

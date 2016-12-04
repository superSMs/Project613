package controller;


import entity.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RegisterRepository registerRepository;

    /**
     * 分派老师
     * @param cid
     * @param uid
     * @return
     */
    @RequestMapping(value = "/people/instructor/{cid}", method = RequestMethod.POST)
    public ResponseInfo setInstructor(@PathVariable Long uid,
                                      @PathVariable Long cid) throws Exception{
        ResponseInfo info = new ResponseInfo();
        List<Register> registers = registerRepository.findByCid(cid);
        Register curReg = null;

        for (int i = 0; i < registers.size(); i++) {
            if (registers.get(i).uid == uid) {
                info.setFailWithInfo("Can't set role");
                return info;
            }
            if (registers.get(i).role == 1L){
                curReg = registers.get(i);
                curReg.uid = uid;
            }
        }
        Register savedReg = registerRepository.save(curReg);
        if(savedReg != null){
            info.setSuccessWithInfo(String.valueOf(savedReg.id));
            return info;
        }else{
            info.setFailWithInfo("Set role failed");
            return info;
        }
    }
}
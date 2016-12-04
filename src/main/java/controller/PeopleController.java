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
public class PeopleController {
    @Autowired
    private UserRepository peopleRepository;

    @Autowired
    private RegisterRepository registerRepository;

    /**
     * 获取学生
     * @param cid
     * @return
     */
    @RequestMapping(value = "/people/student/{cid}", method = RequestMethod.GET)
    public List<User> getStudents(@PathVariable Long cid) throws Exception{
        List<Register> registers = registerRepository.findByCid(cid);
        List<User> students = new ArrayList();
        Register curReg = null;
        User curUser = null;

        for (int i = 0; i < registers.size(); i++) {
            curReg = registers.get(i);
            if (curReg.role == 0L){
                curUser = peopleRepository.findOne(curReg.uid);
                students.add(curUser);
            }
        }
        return students;
    }

    /**
     * 获取老师
     * @param cid
     * @return
     */
    @RequestMapping(value = "/people/instructor/{cid}", method = RequestMethod.GET)
    public List<User> getInstructors(@PathVariable Long cid) throws Exception{
        List<Register> registers = registerRepository.findByCid(cid);
        List<User> instructors = new ArrayList();
        Register curReg = null;
        User curUser = null;

        for (int i = 0; i < registers.size(); i++) {
            curReg = registers.get(i);
            if (curReg.role == 1L){
                curUser = peopleRepository.findOne(curReg.uid);
                instructors.add(curUser);
            }
        }
        return instructors;
    }

    /**
     * 获取全部师生
     * @param id
     * @return
     */
    @RequestMapping(value = "/all/student/", method = RequestMethod.GET)
    public List<User> getAllStudents(@PathVariable Long id) throws Exception{
        List<User> users = peopleRepository.findByIsadmin(0L);
        return users;
    }
}

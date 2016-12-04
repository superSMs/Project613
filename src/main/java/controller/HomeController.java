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
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * 获取所有课程
     * @param id
     * @param admin
     * @return
     */
    @RequestMapping(value = "/courselst/{id}/admin/{admin}", method = RequestMethod.GET)
    public List<Course> getAllCourses(@PathVariable Long id, @PathVariable Long admin) throws Exception{
        List<Course> courses = new ArrayList();
        List<Register> registers = null;
        Course curCourse = null;
        if(1 == admin)
            courses = courseRepository.findByHidden(0L);
        else {
            registers = registerRepository.findByUid(id);
            for (int i = 0; i < registers.size(); i++) {
                curCourse = courseRepository.findOne(registers.get(i).cid);
                if(curCourse.hidden == 0L) courses.add(curCourse);
            }
        }
        return courses;
    }

}

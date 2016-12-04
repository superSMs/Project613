package controller;


import entity.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedList;
import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegisterRepository registerRepository;

    /**
     * 获取课程
     * @param id
     * @return
     */
    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
    public Course getCourse(@PathVariable Long id) throws Exception{
        return courseRepository.findOne(id);
    }

    /**
     * 获取身份
     * @param cid
     * @param uid
     * @return
     */
    @RequestMapping(value = "/course/{uid}/role/{cid}", method = RequestMethod.GET)
    public Register getRole(@PathVariable Long uid,
                            @PathVariable Long cid) throws Exception{
        List<Register> registers = registerRepository.findByUid(uid);
        Register curReg = null;
        for (int i = 0; i < registers.size(); i++)
            if(registers.get(i).cid == cid){
                curReg = registers.get(i);
                break;
            }
        return curReg;
    }

    /**
     * 新建课程
     * @param code
     * @param title
     * @param semester
     */
    @RequestMapping(value = "/course/add", method = RequestMethod.POST)
    public ResponseInfo addCourse(@RequestParam(value="code") String code,
                                  @RequestParam(value="title") String title,
                                  @RequestParam(value="semester") String semester)throws Exception
    {
        ResponseInfo info = new ResponseInfo();
        Course course = new Course();

        course.hidden = 0L;
        course.code = code;
        course.title = title;
        course.semester = semester;
        //course.syllabus = syllabus;

        Course savedCourse = courseRepository.save(course);
        if(savedCourse != null){
            info.setSuccessWithInfo(String.valueOf(savedCourse.id));
            return info;
        }else{
            info.setFailWithInfo("add course failed");
            return info;
        }
    }

    /**
     * 编辑课程
     * @param id
     * @param code
     * @param title
     * @param semester
     */
    @RequestMapping(value = "/course/edit", method = RequestMethod.POST)
    public ResponseInfo editCourse(@RequestParam("id") Long id,
                                  @RequestParam(value="code", required = false) String code,
                                  @RequestParam(value="title", required = false) String title,
                                  @RequestParam(value="semester", required = false) String semester)
            throws Exception
    {
        ResponseInfo info = new ResponseInfo();
        Course course = courseRepository.findOne(id);

        if(course.hidden == 1L){
            info.setFailWithInfo("course already deleted");
            return info;
        }

        if(!code.equals("undefined"))
            course.code = code;
        if(!title.equals("undefined"))
            course.title = title;
        if(!semester.equals("undefined"))
            course.semester = semester;

        Course savedCourse = courseRepository.save(course);
        if(savedCourse != null){
            info.setSuccessWithInfo(String.valueOf(savedCourse.id));
            return info;
        }else{
            info.setFailWithInfo("edit course failed");
            return info;
        }
    }

    /**
     * 删除课程
     * @param id
     */
    @RequestMapping(value = "/course/del", method = RequestMethod.POST)
    public void delCourse(@RequestParam("id") Long id)throws Exception{
        ResponseInfo info = new ResponseInfo();
        Course course = courseRepository.findOne(id);

        courseRepository.delete(course);
    }
}
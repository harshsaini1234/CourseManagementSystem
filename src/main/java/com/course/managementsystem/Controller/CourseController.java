package com.course.managementsystem.Controller;

import com.course.managementsystem.Moddels.Course;
import com.course.managementsystem.Services.CourseServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseServices courseServices;

    public CourseController(CourseServices courseServices) {
        this.courseServices = courseServices;
    }

    @PostMapping("/add")
     public Course createCourse(@RequestBody Course course){
      return courseServices.createCourse(course);

     }
     @GetMapping("/getCourse/{id}")
    public Course getCourseById(@PathVariable long id){
       return courseServices.getCourseById(id);
     }

     @GetMapping("/getAllCourses")
    public List<Course> getAllCourse(){
        return courseServices.getAllCourses();
     }

     @PatchMapping("/partialUpdate/{id}")
     public Course updateCoursePartially(@PathVariable Long id, @RequestBody Course course){
        return courseServices.updateCoursePartially(id,course);

     }

     @PutMapping("/fullUpdate/{id}")
    public Course updateCourseCompletely(@PathVariable Long id,@RequestBody Course course){
        return courseServices.updateCourseCompletely    (id,course);
     }

     @DeleteMapping("/deleteCourse/{id}")
    public String deletecourse(@PathVariable Long id ) {
         return courseServices.deleteCourse(id);
     }



}

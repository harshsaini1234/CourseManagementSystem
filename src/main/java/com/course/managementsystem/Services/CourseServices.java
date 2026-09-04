package com.course.managementsystem.Services;

import com.course.managementsystem.Moddels.Course;
import com.course.managementsystem.Repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServices {
    private final CourseRepository courseRepository;

    public CourseServices(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }


    public Course getCourseById(long id) {
        return courseRepository.findById(id).get();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course updateCoursePartially(Long id, Course updatedCourse) {
       Course existingCourse = courseRepository.findById(id).get();

       if (updatedCourse.getName()!=null){
           existingCourse.setName(updatedCourse.getName());
       }
        if (updatedCourse.getDescription()!=null){
            existingCourse.setDescription(updatedCourse.getDescription());
        }
        if (updatedCourse.getPrice()!=null){
            existingCourse.setPrice(updatedCourse.getPrice());
        }
        return courseRepository.save(existingCourse);


    }

    public Course updateCourseCompletely(Long id, Course updatedCourse) {
        Course exitingCourse = courseRepository.findById(id).get();

        exitingCourse.setName(updatedCourse.getName());
        exitingCourse.setDescription(updatedCourse.getDescription());
        exitingCourse.setPrice(updatedCourse.getPrice());
        return courseRepository.save(exitingCourse);
    }

    public String deleteCourse(Long id) {
         courseRepository.deleteById(id);
         return "Course is deleted successfully";
    }
}

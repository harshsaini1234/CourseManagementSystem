package com.course.managementsystem.Controller;

import com.course.managementsystem.Course_DTO.CourseRequestDTO;
import com.course.managementsystem.Course_DTO.CourseResponseDTO;
import com.course.managementsystem.Moddels.Course;
import com.course.managementsystem.Services.CourseServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     public ResponseEntity<CourseResponseDTO> createCourse( @Valid  @RequestBody CourseRequestDTO course){
        CourseResponseDTO courseCreated = courseServices.createCourse(course);
      return ResponseEntity.status(HttpStatus.CREATED)
              .body(courseCreated);

     }
     @GetMapping("/getCourse/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable long id){

        CourseResponseDTO course = courseServices.getCourseById(id);
       return ResponseEntity.ok(course);
     }

     @GetMapping("/getAllCourses")
    public ResponseEntity<List<CourseResponseDTO>> getAllCourse(){
        return ResponseEntity.ok(courseServices.getAllCourses());
     }

     @PatchMapping("/partialUpdate/{id}")
     public ResponseEntity<CourseResponseDTO> updateCoursePartially(@PathVariable Long id, @RequestBody CourseRequestDTO course){
        return ResponseEntity.ok(courseServices.updateCoursePartially(id,course));

     }

     @PutMapping("/fullUpdate/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourseCompletely(@PathVariable Long id,@RequestBody CourseRequestDTO course){
        return ResponseEntity.ok(courseServices.updateCourseCompletely(id,course));
     }

     @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<Void> deletecourse(@PathVariable Long id ) {
         courseServices.deleteCourse(id);
         return ResponseEntity.noContent().build() ;
     }



}

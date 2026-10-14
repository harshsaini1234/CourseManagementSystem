package com.course.managementsystem.Services;

import com.course.managementsystem.Course_DTO.CourseRequestDTO;
import com.course.managementsystem.Course_DTO.CourseResponseDTO;
import com.course.managementsystem.Course_DTO.CourseResponseDTOTraditional;
import com.course.managementsystem.Exception.CourseNotFoundException;
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

    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO) {
        Course course = mapToEntity(courseRequestDTO);
        Course savedCourse = courseRepository.save(course);
        return mapToResponseDTO(savedCourse);

    }





    public CourseResponseDTO getCourseById(long id) {
        Course course = courseRepository.findById(id).orElseThrow(()->new
                CourseNotFoundException("Course not found with ID :"+id));
        return mapToResponseDTO(course);

    }



    public List<CourseResponseDTO> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public CourseResponseDTO updateCoursePartially(Long id, CourseRequestDTO updatedCourse) {
       Course existingCourse = findCourseOrThrow(id);

       if (updatedCourse.name()!=null){
           existingCourse.setName(updatedCourse.name());
       }
        if (updatedCourse.description()!=null){
            existingCourse.setDescription(updatedCourse.description());
        }
        if (updatedCourse.price()!=null){
            existingCourse.setPrice(updatedCourse.price());
        }
        Course savedCourse = courseRepository.save(existingCourse);
        return mapToResponseDTO(savedCourse);


    }

    public CourseResponseDTO updateCourseCompletely(Long id, CourseRequestDTO updatedCourse) {
        Course exitingCourse = findCourseOrThrow(id);

        exitingCourse.setName(updatedCourse.name());
        exitingCourse.setDescription(updatedCourse.description());
        exitingCourse.setPrice(updatedCourse.price());

        Course savedCourse = courseRepository.save(exitingCourse);
        return mapToResponseDTO(savedCourse);
    }

    public String deleteCourse(Long id) {
        Course exitingCourse = findCourseOrThrow(id);
         courseRepository.delete(exitingCourse);
         
         return "Course is deleted successfully";
    }

    private Course findCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(
                        "Course not found with id: " + id
                ));
    }

    private Course mapToEntity(CourseRequestDTO dto){
        Course course = new Course();
        course.setName(dto.name());
        course.setDescription(dto.description());
        course.setPrice(dto.price());
        return course;

    }
    private CourseResponseDTO mapToResponseDTO(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getPrice()
        );
    }
}

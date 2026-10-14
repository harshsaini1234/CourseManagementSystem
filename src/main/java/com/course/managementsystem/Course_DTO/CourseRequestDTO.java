package com.course.managementsystem.Course_DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequestDTO(

        @NotBlank( message = "Course name is required")
        @Size(min = 3,max = 50,message = "Name must be 3 to 50 characters")
        String name,

        @NotBlank(message = "Description is required")
        @Size(max = 50 , message = "Description Can't exceed 500 characters")
        String description,

        @NotNull(message = "Price is required")
        @Min(value = 0 , message = "Price Can't be negative")
        Integer price
) {


}

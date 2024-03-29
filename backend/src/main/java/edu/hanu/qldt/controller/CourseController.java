package edu.hanu.qldt.controller;

import edu.hanu.qldt.service.impl.CourseServiceImpl;
import edu.hanu.qldt.dto.response.CourseResponseDTO;
import edu.hanu.qldt.model.Course;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@link CourseController} contains all rest api function that need to
 * manage the {@link Course}.
 *
 * @see Course
 * @see CourseServiceImpl
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

    private final CourseServiceImpl courseService;

    @Autowired
    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @ApiOperation(value = "${CourseController.findAll}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Courses don't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/courses/all")
    public List<Course> findAll() {
        return courseService.findAll();
    }

    @ApiOperation(value = "${CourseController.FindById}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Course doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/courses/{id}")
    public Course findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @ApiOperation(value = "${CourseController.getCoursesByTeacherId}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Course doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/courses/teacher/{teacherId}")
    public List<Course> getCoursesByTeacherId(@PathVariable Long teacherId) {
        return  courseService.getCoursesByTeacherId(teacherId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${CourseController.create}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Course cannot created"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PostMapping(value = "/courses/create")
    public Course create(@RequestBody CourseResponseDTO courseResponseDTO) {
        return courseService.create(courseResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${CourseController.update}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Course doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping(value = "/courses/update/{id}")
    public Course update(@PathVariable Long id,
                         @RequestBody CourseResponseDTO courseResponseDTO) {
        return courseService.update(id, courseResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${CourseController.delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Course doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @DeleteMapping(value = "/courses/{id}")
    public String delete(@PathVariable Long id) {
        courseService.delete(id);
        return id.toString();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${CourseController.setCourse}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Course or Student doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping(value = "/courses/setCourse/{studentId}")
    public String setCourse(@PathVariable Long studentId,
                            @RequestBody Long courseId) {
        courseService.setCourse(studentId, courseId);
        return courseId.toString();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${CourseController.unsetCourse}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Course or Student doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping(value = "/courses/unsetCourse/{studentId}")
    public String unsetCourse(@PathVariable Long studentId,
                            @RequestBody Long courseId) {
        courseService.unsetCourse(studentId, courseId);
        return courseId.toString();
    }
}

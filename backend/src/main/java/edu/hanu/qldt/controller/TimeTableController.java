package edu.hanu.qldt.controller;

import edu.hanu.qldt.service.impl.TimeTableServiceImpl;
import edu.hanu.qldt.dto.response.TimeTableEntityResponseDTO;
import edu.hanu.qldt.model.TimeTableEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@link TimeTableController} contains all rest api function that need to
 * manage the application users TimeTable.
 *
 * @see TimeTableEntity
 * @see TimeTableServiceImpl
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeTableController {

    private final TimeTableServiceImpl timeTableService;

    @Autowired
    public TimeTableController(TimeTableServiceImpl timeTableService) {
        this.timeTableService = timeTableService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.hasStudentAccess(principal.id, #id)")
    @ApiOperation(value = "${TimeTableController.getTimeTableByTeacher}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Timetable doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/timetables/student/{id}")
    public TimeTableEntity[][] getTimeTableByStudent(@PathVariable Long id) {
        return timeTableService.getTimeTableByStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.hasTeacherAccess(principal.id, #id)")
    @ApiOperation(value = "${TimeTableController.getTimeTableByTeacher}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Timetable doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/timetables/teacher/{id}")
    public TimeTableEntity[][] getTimeTableByTeacher(@PathVariable Long id) {
        return timeTableService.getTimeTableByTeacher(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${TimeTableController.getTimeTableEntitiesByCourse}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Timetable doesn't found to course"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/timetables/course/{courseId}")
    public List<TimeTableEntity> getTimeTableEntitiesByCourse(@PathVariable Long courseId) {
        return timeTableService.getTimeTableEntitiesByCourse(courseId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${TimeTableController.findById}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "TimetableEntity doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/timetables/{id}")
    public TimeTableEntity findById(@PathVariable Long id) {
        return timeTableService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${TimeTableController.create}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "TimetableEntity cannot created"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/timetables/create")
    public TimeTableEntity create(@RequestBody TimeTableEntityResponseDTO timeTableEntityResponseDTO) {
        return timeTableService.create(timeTableEntityResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${TimeTableController.update}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "TimetableEntity doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping(value = "/timetables/update/{id}")
    public TimeTableEntity update(@PathVariable Long id,
                                  @RequestBody TimeTableEntityResponseDTO timeTableEntityResponseDTO) {
        return timeTableService.update(id, timeTableEntityResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${TimeTableController.delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "TimetableEntity doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @DeleteMapping(value = "/timetables/{id}")
    public String delete(@PathVariable Long id) {
        timeTableService.delete(id);
        return id.toString();
    }


}

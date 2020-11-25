package edu.hanu.qldt.controller;

import edu.hanu.qldt.service.impl.HeadTeacherServiceImpl;
import edu.hanu.qldt.dto.ClassroomCourseResultDTO;
import edu.hanu.qldt.dto.FailedStudentDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@link HeadTeacherController} contains rest api function that need to
 * manage the HeadTeacher.
 *
 * @see HeadTeacherServiceImpl
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class HeadTeacherController {

    private final HeadTeacherServiceImpl headTeacherService;

    @Autowired
    public HeadTeacherController(HeadTeacherServiceImpl headTeacherService) {
        this.headTeacherService = headTeacherService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${HeadTeacherController.findFailedStudentsInClass}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "failed students don't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/headteacher/find-failed/{classroomId}")
    public List<FailedStudentDTO> findFailedStudentsInClass(@PathVariable Long classroomId) {
        return headTeacherService.findFailedStudentsInClass(classroomId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${HeadTeacherController.findFailedStudentsInClass}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "failed students don't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/headteacher/show-result/{classroomId}")
    public List<ClassroomCourseResultDTO> showResultByCourse(@PathVariable Long classroomId) {
        return headTeacherService.showResultByCourse(classroomId);
    }
}

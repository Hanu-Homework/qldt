package edu.hanu.qldt.controller;

import edu.hanu.qldt.service.impl.AttendanceServiceImpl;
import edu.hanu.qldt.dto.AttendanceDTO;
import edu.hanu.qldt.dto.response.AttendanceResponseDTO;
import edu.hanu.qldt.model.Attendance;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@link AttendanceController} contains all rest api function that need to
 * manage the {@link Attendance}.
 *
 * @see Attendance
 * @see AttendanceServiceImpl
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttendanceController {

    private final AttendanceServiceImpl attendanceService;

    @Autowired
    public AttendanceController(AttendanceServiceImpl attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER') or" +
            "@securityService.hasStudentAccess(principal.id, #studentId)")
    @ApiOperation(value = "${AttendanceController.getAllByStudent}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Attendances don't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/attendances/all/{studentId}")
    public List<Attendance> getAllByStudent(@PathVariable Long studentId) {
        System.out.println("Invoked");
        return attendanceService.getAllByStudent(studentId);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${AttendanceController.create}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Attendances cannot created"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PostMapping(value = "/attendances/create")
    public List<Attendance> create(@RequestBody List<AttendanceResponseDTO> attendanceResponseDTOS) {
        return attendanceService.create(attendanceResponseDTOS);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${AttendanceController.delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Attendance doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @DeleteMapping(value = "/attendances/{id}")
    public String delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return id.toString();
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${AttendanceController.verify}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Attendance doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping(value = "/attendances/verify/{id}")
    public String verify(@PathVariable Long id) {
        attendanceService.verify(id);
        return id.toString();
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${AttendanceController.getAllAttendancesByClassroom}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Classroom doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/attendances/classroom/{classroomId}")
    public List<Attendance> getAllAttendancesByClassroom(@PathVariable Long classroomId) {
        return  attendanceService.getAllAttendancesByClassroom(classroomId);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${AttendanceController.makeAttendanceFormToClassroom}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Classroom doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/attendances/{classroomId}")
        public List<AttendanceDTO> makeAttendanceFormToClassroom(@PathVariable Long classroomId) {
        return attendanceService.makeAttendanceFormToClassroom(classroomId);
    }
}

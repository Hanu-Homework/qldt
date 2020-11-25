package edu.hanu.qldt.controller;

import edu.hanu.qldt.service.impl.RemarkServiceImpl;
import edu.hanu.qldt.dto.response.RemarkResponseDTO;
import edu.hanu.qldt.model.Remark;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@link RemarkController} contains all rest api function that need to
 * manage the {@link Remark}.
 *
 * @see Remark
 * @see RemarkServiceImpl
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RemarkController {

    private final RemarkServiceImpl remarkService;

    @Autowired
    public RemarkController(RemarkServiceImpl remarkService) {
        this.remarkService = remarkService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')" +
            "or @securityService.hasStudentAccess(principal.id, #studentId)")
    @ApiOperation(value = "${RemarkController.findAllByStudent}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Remarks don't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/remarks/student/{studentId}")
    public List<Remark> findAllByStudent(@PathVariable Long studentId) {
        return remarkService.findAllByStudent(studentId);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')" +
//            "or @securityService.hasStudentAccess(principal.id, #student_id)")
    @ApiOperation(value = "${RemarkController.findById}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Remark doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/remarks/{id}")
    public Remark findById(@PathVariable Long id) {
        System.out.println("Invoked findById");
        return remarkService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${RemarkController.create}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Remark cannot created"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PostMapping(value = "/remarks/create")
    public Remark create(@RequestBody RemarkResponseDTO remarkResponseDTO) {
        return remarkService.create(remarkResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${RemarkController.update}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Remark doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping(value = "/remarks/update/{id}")
    public Remark update(@PathVariable Long id,
                       @RequestBody RemarkResponseDTO remarkResponseDTO) {
        return remarkService.update(id, remarkResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_HEADTEACHER')")
    @ApiOperation(value = "${RemarkController.delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Remark doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @DeleteMapping(value = "/remarks/{id}")
    public String delete(@PathVariable Long id) {
        remarkService.delete(id);
        return id.toString();
    }
}

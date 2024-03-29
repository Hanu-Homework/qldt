package edu.hanu.qldt.controller;

import edu.hanu.qldt.service.impl.AdminServiceImpl;
import edu.hanu.qldt.model.archive.Archive;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The {@link AdminController} contains all rest api function that need to
 * manage the Admin.
 *
 * @see AdminServiceImpl
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${AdminController.newYear}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The new year cannot created"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    @GetMapping(value = "/admin/newYear")
    public String newYear() {
        adminService.newYear();
        return "Done";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${AdminController.createArchive}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The archive cannot created"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    @GetMapping(value = "/admin/createArchive")
    public void createArchive() {
        adminService.createArchive();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${AdminController.getArchive}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Archives doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    @GetMapping(value = "/admin/archives")
    public List<Archive> getArchive() {
        return adminService.getArchive();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${AdminController.finished}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Classroom doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    @GetMapping(value = "/admin/finished/{classroomId}")
    public String finished(@PathVariable Long classroomId) {
        adminService.finished(classroomId);
        return "Finished";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${AdminController.getArchiveByArchiveId}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Archive doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    @GetMapping(value = "/admin/archive/{id}")
    public Archive getArchiveByArchiveId(@PathVariable Long id) {
        return adminService.getArchiveById(id);
    }
}

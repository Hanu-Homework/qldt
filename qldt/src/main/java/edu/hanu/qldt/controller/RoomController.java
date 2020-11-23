package edu.hanu.qldt.controller;
import edu.hanu.qldt.service.impl.RoomServiceImpl;
import edu.hanu.qldt.dto.response.RoomResponseDTO;
import edu.hanu.qldt.model.Room;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@link RoomController} contains all rest api function that need to
 * manage the {@link Room}.
 *
 * @see Room
 * @see RoomServiceImpl
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

    private final RoomServiceImpl roomService;

    @Autowired
    public RoomController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @ApiOperation(value = "${RoomController.findAll}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Rooms don't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/rooms/all")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @ApiOperation(value = "${RoomController.FindById}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Room doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/rooms/{id}")
    public Room FindById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${RoomController.create}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Room cannot created"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PostMapping(value = "/rooms/create")
    public Room create(@RequestBody RoomResponseDTO roomResponseDTO) {
        return roomService.create(roomResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${RoomController.delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Room doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @DeleteMapping(value = "/rooms/{id}")
    public String delete(@PathVariable Long id) {
        roomService.delete(id);
        return id.toString();
    }
}

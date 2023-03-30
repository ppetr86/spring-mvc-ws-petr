package com.shopapp.api.controller;

import com.shopapp.api.converter.RoleExportConverter;
import com.shopapp.data.entity.RoleEntity;
import com.shopapp.service.RoleDao;
import com.shopapp.data.entitydto.out.RoleDtoOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
@Tag(
        name = "Read REST APIs for RoleEntity",
        description = "CRUD REST APIs - Create Role, Update Role, Get Role, Get All Roles, Delete Role"
)
public class RoleController {

    private final RoleDao roleDao;

    @Operation(summary = "Get RoleEntity REST API",
            description = "Get RoleEntity REST API is used to view the resource in database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<RoleDtoOut> getRoleById(@PathVariable String id) {
        var result = new RoleExportConverter().convertToDtoOut(roleDao.loadById(UUID.fromString(id)));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get List of RoleEntity REST API",
            description = "Get RoleEntity REST API is used to view the resource in database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<RoleDtoOut>> getRoles(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "25") int limit) {
        List<RoleEntity> roles = roleDao.loadAll(PageRequest.of(page - 1, limit)).getContent();

        var result = new RoleExportConverter().convertToListDtoOut(roles);

        return ResponseEntity.ok(result);
    }
}

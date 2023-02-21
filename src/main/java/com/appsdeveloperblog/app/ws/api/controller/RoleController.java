package com.appsdeveloperblog.app.ws.api.controller;

import com.appsdeveloperblog.app.ws.api.converter.RoleExportConverter;
import com.appsdeveloperblog.app.ws.service.RoleDao;
import com.appsdeveloperblog.app.ws.shared.dto.RoleDtoOut;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleDao roleDao;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<RoleDtoOut> getRoleById(@PathVariable String id) {
        var result = new RoleExportConverter().convertToDtoOut(roleDao.loadById(UUID.fromString(id)));
        return ResponseEntity.ok(result);
    }
}

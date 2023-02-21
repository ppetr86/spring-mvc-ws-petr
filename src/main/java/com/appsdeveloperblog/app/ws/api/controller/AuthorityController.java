package com.appsdeveloperblog.app.ws.api.controller;

import com.appsdeveloperblog.app.ws.api.converter.AuthorityExportConverter;
import com.appsdeveloperblog.app.ws.service.AuthorityDao;
import com.appsdeveloperblog.app.ws.shared.dto.AuthorityDtoOut;
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
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityDao authorityDao;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AuthorityDtoOut> getAuthorityById(@PathVariable String id) {
        var result = new AuthorityExportConverter().convertToDtoOut(authorityDao.loadById(UUID.fromString(id)));
        return ResponseEntity.ok(result);
    }
}
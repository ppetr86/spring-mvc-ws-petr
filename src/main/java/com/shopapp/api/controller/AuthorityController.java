package com.shopapp.api.controller;

import com.shopapp.api.converter.AuthorityExportConverter;
import com.shopapp.service.AuthorityDao;
import com.shopapp.data.entitydto.out.AuthorityDtoOut;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/authorities")
@Tag(
        name = "Read REST APIs for AuthorityEntity",
        description = "CRUD REST APIs - Create Authority, Update Authority, Get Authority, Get All Authoritys, Delete Authority"
)
public class AuthorityController {

    private final AuthorityDao authorityDao;

    @Operation(summary = "Get AuthorityEntity REST API",
            description = "Get AuthorityEntity REST API is used to view the resource in database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AuthorityDtoOut> getAuthorityById(@PathVariable String id) {
        var result = new AuthorityExportConverter().convertToDtoOut(authorityDao.loadById(UUID.fromString(id)));
        return ResponseEntity.ok(result);
    }
}
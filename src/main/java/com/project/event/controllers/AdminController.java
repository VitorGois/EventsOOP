package com.project.event.controllers;

import com.project.event.dtos.admin.AdminDto;
import com.project.event.dtos.admin.AdminInsertDto;
import com.project.event.dtos.admin.AdminUpdateDto;
import com.project.event.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping()
    public ResponseEntity<Page<AdminDto>> getAdmins(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "email", defaultValue = "") String email
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<AdminDto> adminList = this.adminService.readAdminList(pageRequest, name, email);

        return ResponseEntity.ok(adminList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable() Long id) {
        AdminDto adminDto = this.adminService.readAdminById(id);
        return ResponseEntity.ok(adminDto);
    }

    @PostMapping()
    public ResponseEntity<AdminDto> postAdmin(@Valid() @RequestBody() AdminInsertDto adminInsertDto) {
        AdminDto adminDto = this.adminService.createAdmin(adminInsertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adminDto.getId()).toUri();
        return ResponseEntity.created(uri).body(adminDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> putAdmin(@Valid() @RequestBody() AdminUpdateDto adminUpdateDto, @PathVariable() Long id) {
        AdminDto adminDto = this.adminService.updateAdmin(id, adminUpdateDto);
        return ResponseEntity.ok().body(adminDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable() Long id) {
        this.adminService.removeAdmin(id);
        return ResponseEntity.noContent().build();
    }

}

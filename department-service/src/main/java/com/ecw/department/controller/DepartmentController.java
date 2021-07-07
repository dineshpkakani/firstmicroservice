package com.ecw.department.controller;

import com.ecw.department.entity.Department;
import com.ecw.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

      @PostMapping("/save")
    public Department saveDepartment(@RequestBody Department department){
          log.info("Inside save department inside department controller");
        return  departmentService.saveDepartment(department);
    }
    @GetMapping("/{id}")
    public Department findByDepartmentd(@PathVariable("id") Long departmentId){
        log.info("inside findbydepartment inside department service");
        return departmentService.findDepartmentById(departmentId);
    }
}

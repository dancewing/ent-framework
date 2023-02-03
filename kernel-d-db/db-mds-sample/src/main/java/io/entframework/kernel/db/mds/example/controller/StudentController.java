package io.entframework.kernel.db.mds.example.controller;

import io.entframework.kernel.db.mds.example.controller.base.BaseStudentController;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResource(name = "学生")
public class StudentController extends BaseStudentController {
}
package io.entframework.kernel.db.mds.example.controller;

import io.entframework.kernel.db.mds.example.controller.base.BaseTeacherController;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResource(name = "教师")
public class TeacherController extends BaseTeacherController {

}
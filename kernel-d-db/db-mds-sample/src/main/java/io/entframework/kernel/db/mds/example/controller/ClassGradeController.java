package io.entframework.kernel.db.mds.example.controller;

import io.entframework.kernel.db.mds.example.controller.base.BaseClassGradeController;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResource(name = "班级")
public class ClassGradeController extends BaseClassGradeController {
}
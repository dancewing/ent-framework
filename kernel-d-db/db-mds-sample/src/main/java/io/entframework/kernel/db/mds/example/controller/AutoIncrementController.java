package io.entframework.kernel.db.mds.example.controller;

import io.entframework.kernel.db.mds.example.controller.base.BaseAutoIncrementController;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResource(name = "AutoTest")
public class AutoIncrementController extends BaseAutoIncrementController {
}
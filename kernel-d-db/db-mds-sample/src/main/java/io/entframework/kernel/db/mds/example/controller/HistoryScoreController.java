package io.entframework.kernel.db.mds.example.controller;

import io.entframework.kernel.db.mds.example.controller.base.BaseHistoryScoreController;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResource(name = "考试记录")
public class HistoryScoreController extends BaseHistoryScoreController {

}
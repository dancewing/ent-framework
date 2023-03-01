package io.entframework.kernel.oss.controller;

import io.entframework.kernel.file.minio.MinIoFileOperator;
import io.entframework.kernel.oss.service.BucketHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;

@RestController
@Slf4j
public class HomeController {

	@Resource
	private MinIoFileOperator minIoFileOperator;

	@GetMapping("/**")
	public void index(HttpServletRequest request, HttpServletResponse response) {
		log.info(request.getRemoteHost());
		log.info(request.getRemoteAddr());
		log.info(request.getRequestURI());
		log.info(request.getPathInfo());
		String filePath = request.getRequestURI();
		String fileName = StringUtils.substringAfterLast(filePath, "/");
		byte[] fileBytes = minIoFileOperator.getFileBytes(BucketHolder.get(), request.getRequestURI());
		// response.setContentType((String) record.get("mime"));
		response.setCharacterEncoding("utf-8");
		response.setContentLength(fileBytes.length);
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);

		try {
			OutputStream out = response.getOutputStream();
			out.write(fileBytes);
			out.close();
			response.flushBuffer();
		}
		catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

}

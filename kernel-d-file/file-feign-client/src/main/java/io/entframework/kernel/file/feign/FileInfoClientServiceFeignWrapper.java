package io.entframework.kernel.file.feign;

import io.entframework.kernel.file.api.FileInfoClientApi;
import io.entframework.kernel.file.api.pojo.request.SysFileInfoRequest;
import io.entframework.kernel.file.api.pojo.response.SysFileInfoResponse;
import io.entframework.kernel.rule.function.Try;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileInfoClientServiceFeignWrapper implements FileInfoClientApi {

    private final FileInfoClientApi fileInfoClient;

    public FileInfoClientServiceFeignWrapper(FileInfoClientApi fileInfoClient) {
        this.fileInfoClient = fileInfoClient;
    }

    @Override
    public SysFileInfoResponse getFileInfoWithoutContent(Long fileId) {
        return fileInfoClient.getFileInfoWithoutContent(fileId);
    }

    @Override
    public String getFileAuthUrl(Long fileId) {
        return fileInfoClient.getFileAuthUrl(fileId);
    }

    @Override
    public String getFileAuthUrl(Long fileId, String token) {
        return Try.call(() -> fileInfoClient.getFileAuthUrl(fileId, token)).ifFailure(e -> {
            log.info("fileInfoClient.getFileAuthUrl error: ", e);
        }).toOptional().orElse(null);
    }

    @Override
    public String getFileUnAuthUrl(Long fileId) {
        return fileInfoClient.getFileUnAuthUrl(fileId);
    }

    @Override
    public ResponseData<Void> deleteReally(SysFileInfoRequest fileInfoRequest) {
        return fileInfoClient.deleteReally(fileInfoRequest);
    }

}

package io.entframework.kernel.file.modular.factory;

import io.entframework.kernel.file.api.FileOperatorApi;
import io.entframework.kernel.file.api.enums.FileStorageEnum;
import io.entframework.kernel.file.api.exception.FileException;
import io.entframework.kernel.file.api.exception.enums.FileExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jeff_qian
 */
@Slf4j
public class FileOperatorFactory {

    private static final Map<FileStorageEnum, FileOperatorApi> fileOperatorApis = new ConcurrentHashMap<>();

    public FileOperatorFactory(Collection<FileOperatorApi> operatorApis) {
        if (operatorApis != null) {
            Iterator<FileOperatorApi> iterator = operatorApis.iterator();
            while (iterator.hasNext()) {
                FileOperatorApi fileOperatorApi = iterator.next();
                if (fileOperatorApi.getFileLocationEnum() == null) {
                    throw new FileException(FileExceptionEnum.FILE_STORAGE_TYPE_NOT_NULL);
                }
                fileOperatorApis.put(fileOperatorApi.getFileLocationEnum(), fileOperatorApi);
            }
        }
    }

    public FileOperatorApi getFileOperatorApi(FileStorageEnum fileStorageEnum) {
        Assert.notNull(fileStorageEnum, "参数 FileStorageEnum 不能为null");
        if (fileOperatorApis.containsKey(fileStorageEnum)) {
            return fileOperatorApis.get(fileStorageEnum);
        }
        throw new FileException(FileExceptionEnum.FILE_STORAGE_NOT_FOUND, fileStorageEnum.getValue());
    }

    public FileOperatorApi getFileOperatorApi(Integer fileLocation) {
        Assert.notNull(fileLocation, "参数 FileStorage 不能为null");
        return getFileOperatorApi(FileStorageEnum.getStorage(fileLocation));
    }

}

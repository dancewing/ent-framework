/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.file.api.util;

import cn.hutool.core.text.CharSequenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件类型识别工具
 *
 * @date 2020/11/29 14:00
 */
public class PicFileTypeUtil {

    private static final List<String> PIC_TYPES;

    static {
        PIC_TYPES = new ArrayList<>();
        PIC_TYPES.add("jpg");
        PIC_TYPES.add("png");
        PIC_TYPES.add("jpeg");
        PIC_TYPES.add("tif");
        PIC_TYPES.add("gif");
        PIC_TYPES.add("bmp");
    }

    /**
     * 根据文件名称获取文件是否为图片类型
     * @param fileName 文件名称
     * @return boolean true-是图片类型，false-不是图片类型
     * @date 2020/11/29 14:04
     */
    public static boolean getFileImgTypeFlag(String fileName) {
        if (CharSequenceUtil.isEmpty(fileName)) {
            return false;
        }

        for (String picType : PIC_TYPES) {
            if (fileName.toLowerCase().endsWith(picType)) {
                return true;
            }
        }

        return false;
    }

}

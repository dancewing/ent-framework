/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.internal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedFile;
import org.mybatis.generator.api.GeneratedPlainFile;
import org.mybatis.generator.exception.ShellException;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlainTextFileMerger {

    public static String getMergedSource(GeneratedFile generatedFile, File existingFile) throws ShellException {
        if (generatedFile instanceof GeneratedPlainFile generatedPlainFile) {

            List<String> existingFileLines = new ArrayList<>();
            try {
                existingFileLines = FileUtils.readLines(existingFile, StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                return generatedFile.getFormattedContent();
            }
            if (existingFileLines.isEmpty()) {
                return generatedFile.getFormattedContent();
            }
            Set<String> contents = new HashSet<>(existingFileLines);
            List<String> bodyLines = generatedPlainFile.getBodyLine();
            contents.addAll(bodyLines);
            return StringUtils.join(contents, '\n');
        }

        return generatedFile.getFormattedContent();
    }

}

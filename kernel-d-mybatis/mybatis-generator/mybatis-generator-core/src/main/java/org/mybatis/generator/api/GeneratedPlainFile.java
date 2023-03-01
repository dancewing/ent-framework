/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package org.mybatis.generator.api;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedFile;
import org.mybatis.generator.api.WriteMode;

import java.util.ArrayList;
import java.util.List;

public class GeneratedPlainFile extends GeneratedFile {

    private final String fileName;

    private final String targetPackage;

    private List<String> bodyLine = new ArrayList<>();

    private boolean isMergeable;

    private WriteMode writeMode;

    public GeneratedPlainFile(String targetProject, String fileName, String targetPackage) {
        super(targetProject);
        this.fileName = fileName;
        this.targetPackage = targetPackage;
    }

    public void addBodyLine(String line) {
        this.bodyLine.add(line);
    }

    @Override
    public String getFormattedContent() {
        return StringUtils.join(bodyLine, "\n");
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getTargetPackage() {
        return this.targetPackage;
    }

    @Override
    public boolean isMergeable() {
        return this.isMergeable;
    }

    @Override
    public String getFileEncoding() {
        return "UTF-8";
    }

    @Override
    public WriteMode getWriteMode() {
        return this.writeMode;
    }

    public void setMergeable(boolean mergeable) {
        isMergeable = mergeable;
    }

    public void setWriteMode(WriteMode writeMode) {
        this.writeMode = writeMode;
    }

    public List<String> getBodyLine() {
        return bodyLine;
    }

}

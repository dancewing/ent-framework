/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.ent.gateway.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ComparisonChain;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwaggerResource implements Comparable<SwaggerResource> {
    private String name;
    private String url;
    private String swaggerVersion;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Use url going forward rather than location
     *
     * @return url
     * @since 2.8.0
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("swaggerVersion")
    public String getSwaggerVersion() {
        return swaggerVersion;
    }

    public void setSwaggerVersion(String swaggerVersion) {
        this.swaggerVersion = swaggerVersion;
    }

    @Override
    public int compareTo(SwaggerResource other) {
        return ComparisonChain.start()
                .compare(this.swaggerVersion, other.swaggerVersion)
                .compare(this.name, other.name)
                .result();
    }
}

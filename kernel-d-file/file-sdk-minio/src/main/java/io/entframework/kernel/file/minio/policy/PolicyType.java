/*
 * Minio Java SDK for Amazon S3 Compatible Cloud Storage, (C) 2016 Minio, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.entframework.kernel.file.minio.policy;

public enum PolicyType {

    NONE("none"), READ_ONLY("readonly"), READ_WRITE("readwrite"), WRITE_ONLY("writeonly");

    private final String value;

    private PolicyType(final String value) {
        this.value = value;
    }

    /**
     * Returns the policyType value.
     */
    public String getValue() {
        return value;
    }

}

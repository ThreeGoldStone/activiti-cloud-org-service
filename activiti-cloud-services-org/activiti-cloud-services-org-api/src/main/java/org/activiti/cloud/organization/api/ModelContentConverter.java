/*
 * Copyright 2019 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.organization.api;

import java.util.Optional;

/**
 * Model content converter interface
 */
public interface ModelContentConverter<T extends ModelContent> {

    /**
     * Get handled model type by this converter.
     * @return handled model type
     */
    ModelType getHandledModelType();

    /**
     * Convert a bytes array to the handled model content entity.
     * @param bytes the bytes to convert
     * @return the model content, or {@link Optional#empty()}
     */
    Optional<T> convertToModelContent(byte[] bytes);

    /**
     * Convert an instance of the model content to bytes array
     * @param modelContent the model content instance to convert
     * @return the bytes array
     */
    byte[] convertToBytes(T modelContent);

    /**
     * Convert a bytes array to the handled model content entity, set the given id, and convert back to bytes array.
     * If the id is already the expected one, it returns the given bytes unchanged.
     * @param bytes the bytes to convert and fix
     * @param modelContentId the correct model content id to set
     * @return the fixed bytes array
     */
    default byte[] convertAndFixModelContentId(byte[] bytes,
                                               String modelContentId) {
        return bytes;
    }
}

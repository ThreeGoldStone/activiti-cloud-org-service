/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
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

package org.activiti.cloud.services.organization.rest.assembler;

import org.activiti.cloud.organization.api.ModelType;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Component;

/**
 * Rel provider for {@link ModelType}
 */
@Component
public class ModelTypeRelProvider implements RelProvider {

    public static final String COLLECTION_RESOURCE_REL = "model-types";

    private static final String ITEM_RESOURCE_REL = "model-type";

    @Override
    public String getItemResourceRelFor(Class<?> type) {
        return ITEM_RESOURCE_REL;
    }

    @Override
    public String getCollectionResourceRelFor(Class<?> type) {
        return COLLECTION_RESOURCE_REL;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ModelType.class.isAssignableFrom(aClass);
    }
}

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

package org.activiti.cloud.services.organization.converter;

import java.util.Optional;

import org.activiti.cloud.organization.api.ConnectorModelType;
import org.activiti.cloud.organization.api.ModelContentConverter;
import org.activiti.cloud.organization.api.ModelType;
import org.activiti.cloud.organization.converter.JsonConverter;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link ModelContentConverter} for connectors models
 */
@Component
public class ConnectorModelContentConverter implements ModelContentConverter<ConnectorModelContent> {

    private final ConnectorModelType connectorModelType;

    private final JsonConverter<ConnectorModelContent> connectorModelContentJsonConverter;

    public ConnectorModelContentConverter(ConnectorModelType connectorModelType,
                                          JsonConverter<ConnectorModelContent> connectorModelContentJsonConverter) {
        this.connectorModelType = connectorModelType;
        this.connectorModelContentJsonConverter = connectorModelContentJsonConverter;
    }

    @Override
    public ModelType getHandledModelType() {
        return connectorModelType;
    }

    @Override
    public Optional<ConnectorModelContent> convertToModelContent(byte[] bytes) {
        return Optional.ofNullable(connectorModelContentJsonConverter.convertToEntity(bytes));
    }

    @Override
    public byte[] convertToBytes(ConnectorModelContent modelContent) {
        return connectorModelContentJsonConverter.convertToJsonBytes(modelContent);
    }
}

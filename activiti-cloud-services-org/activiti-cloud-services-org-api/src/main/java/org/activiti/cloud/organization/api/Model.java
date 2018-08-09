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

package org.activiti.cloud.organization.api;

/**
 * Interface for models
 */
public interface Model<A extends Application, U> extends Auditable<U> {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    ModelType getType();

    void setType(ModelType type);

    A getApplication();

    void setApplication(A application);

    String getVersion();

    void setVersion(String version);

    String getContentType();

    void setContentType(String contentType);

    String getContent();

    void setContent(String content);
}
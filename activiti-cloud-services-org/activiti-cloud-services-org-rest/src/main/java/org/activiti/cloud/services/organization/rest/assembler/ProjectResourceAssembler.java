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

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.activiti.cloud.organization.api.Project;
import org.activiti.cloud.organization.core.error.ModelingException;
import org.activiti.cloud.services.organization.rest.controller.ProjectController;
import org.activiti.cloud.services.organization.rest.controller.ModelController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.activiti.cloud.organization.api.ProcessModelType.PROCESS;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Assembler for {@link Project} resource
 */
@Component
public class ProjectResourceAssembler implements ResourceAssembler<Project, Resource<Project>> {

    @Override
    public Resource<Project> toResource(Project project) {
        return new Resource<>(
                project,
                linkTo(methodOn(ProjectController.class).getProject(project.getId())).withSelfRel(),
                getExportProjectLink(project.getId()),
                getImportProjectModelLink(project.getId()),
                linkTo(methodOn(ModelController.class).getModels(project.getId(),
                                                                 PROCESS,
                                                                 Pageable.unpaged())).withRel("models"));
    }

    private Link getImportProjectModelLink(String projectId) {
        try {
            return linkTo(methodOn(ModelController.class).importModel(projectId,
                                                                      PROCESS,
                                                                      null)).withRel("import");
        } catch (IOException e) {
            throw new ModelingException(e);
        }
    }

    private Link getExportProjectLink(String projectId) {
        try {
            return linkTo(ProjectController.class,
                          ProjectController.class.getMethod("exportProject",
                                                            HttpServletResponse.class,
                                                            String.class,
                                                            boolean.class),
                          projectId)
                    .withRel("export");
        } catch (NoSuchMethodException e) {
            throw new ModelingException(e);
        }
    }
}

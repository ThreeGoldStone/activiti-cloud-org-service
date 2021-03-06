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

package org.activiti.cloud.services.organization.rest.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiParam;
import org.activiti.cloud.alfresco.data.domain.AlfrescoPagedResourcesAssembler;
import org.activiti.cloud.organization.api.Project;
import org.activiti.cloud.services.common.file.FileContent;
import org.activiti.cloud.services.organization.rest.api.ProjectRestApi;
import org.activiti.cloud.services.organization.rest.assembler.ProjectResourceAssembler;
import org.activiti.cloud.services.organization.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.activiti.cloud.services.common.util.HttpUtils.writeFileToResponse;

/**
 * Controller for {@link Project} resources
 */
@RestController
public class ProjectController implements ProjectRestApi {

    private final ProjectService projectService;

    private final ProjectResourceAssembler resourceAssembler;

    private final AlfrescoPagedResourcesAssembler<Project> pagedResourcesAssembler;

    @Autowired
    public ProjectController(ProjectService projectService,
                             ProjectResourceAssembler resourceAssembler,
                             AlfrescoPagedResourcesAssembler<Project> pagedResourcesAssembler) {
        this.projectService = projectService;
        this.resourceAssembler = resourceAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedResources<Resource<Project>> getProjects(Pageable pageable) {
        return pagedResourcesAssembler.toResource(
                pageable,
                projectService.getProjects(pageable),
                resourceAssembler);
    }

    @Override
    public Resource<Project> getProject(
            @ApiParam(GET_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId) {
        return resourceAssembler.toResource(findProjectById(projectId));
    }

    @Override
    public Resource<Project> createProject(
            @ApiParam(CREATE_PROJECT_PARAM_DESCR)
            @RequestBody Project project) {
        return resourceAssembler.toResource(projectService.createProject(project));
    }

    @Override
    public Resource<Project> updateProject(
            @ApiParam(UPDATE_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId,
            @ApiParam(UPDATE_PROJECT_PARAM_DESCR)
            @RequestBody Project project) {
        Project projectToUpdate = findProjectById(projectId);
        return resourceAssembler.toResource(projectService.updateProject(projectToUpdate,
                                                                         project));
    }

    @Override
    public void deleteProject(
            @ApiParam(DELETE_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId) {
        projectService.deleteProject(findProjectById(projectId));
    }

    @Override
    public Resource<Project> importProject(
            @ApiParam(IMPORT_PROJECT_FILE_PARAM_DESCR)
            @RequestParam(UPLOAD_FILE_PARAM_NAME) MultipartFile file) throws IOException {
        return resourceAssembler.toResource(projectService.importProject(file));
    }

    @Override
    public void exportProject(
            HttpServletResponse response,
            @ApiParam(EXPORT_PROJECT_ID_PARAM_DESCR)
            @PathVariable String projectId,
            @ApiParam(ATTACHMENT_API_PARAM_DESCR)
            @RequestParam(name = EXPORT_AS_ATTACHMENT_PARAM_NAME,
                    required = false,
                    defaultValue = "true") boolean attachment) throws IOException {
        Project project = findProjectById(projectId);
        FileContent fileContent = projectService.exportProject(project);
        writeFileToResponse(response,
                            fileContent,
                            attachment);
    }

    public Project findProjectById(String projectId) {
        return projectService.findProjectById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + projectId));
    }
}

/*
 * Copyright 2017 Alfresco, Inc. and/or its affiliates.
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

package org.activiti.cloud.services.organization.rest.client;

import static org.activiti.cloud.organization.core.model.Model.ModelType.PROCESS_MODEL;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.activiti.cloud.organization.core.model.Model;
import org.activiti.cloud.organization.core.model.ModelData;
import org.activiti.cloud.services.organization.config.Application;
import org.activiti.cloud.services.organization.jpa.ModelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Consumer side tests for communication with process models service
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@AutoConfigureStubRunner(
        ids = "org.activiti.cloud:activiti-cloud-services-process-model-rest:+:stubs:6565",
        workOffline = true)
public class ProcessModelServiceRestClientTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ModelRepository modelRepository;

    @Before
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test
    public void testCreateProcessModel() throws Exception {
        Model processModel = new Model("newprocessModel",
                                       "newProcesModelName",
                                       PROCESS_MODEL,
                                       "newProcesModelId");

        String json = new ObjectMapper().writeValueAsString(processModel);
        given()
                .body(json)
                .post("/models")
                .then().expect(status().isCreated());
    }

    @Test
    public void testUpdateProcessModel() throws Exception {
        Model processModel = new Model("processModel_id2",
                                       "newProcesModelNameUpdated",
                                       PROCESS_MODEL,
                                       "testProcesModelId");
        modelRepository.save(processModel);

        String json = new ObjectMapper().writeValueAsString(processModel);
        given()
                .body(json)
                .put("/models/processModel_id2")
                .then().expect(status().isNoContent());
    }

    @Test
    public void testGetProcessModel() throws Exception {
        Model processModel = new Model("processModel_id",
                                       "testProcesModelName",
                                       PROCESS_MODEL,
                                       "testProcesModelId");
        modelRepository.save(processModel);

        given()
                .get("/models/processModel_id")
                .then().expect(status().isOk())
                .and().extract().response().print();
    }
}

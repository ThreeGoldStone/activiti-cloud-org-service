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

package org.activiti.cloud.services.organization.mock;

import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Is the value equal to another value
 */
public class IsObjectEquals {

    public static Matcher<Object> isIntegerEquals(int operand) {
        return equalTo(operand);
    }

    public static Matcher<Object> isBooleanEquals(boolean operand) {
        return equalTo(operand);
    }

    public static Matcher<Object> isDateEquals(Object operand) {
        return equalTo(operand);
    }
}
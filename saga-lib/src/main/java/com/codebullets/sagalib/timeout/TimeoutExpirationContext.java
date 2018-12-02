/*
 * Copyright 2018 Stefan Domnanovits
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.codebullets.sagalib.timeout;

import com.codebullets.sagalib.HeaderName;

import java.util.Map;

/**
 * Exposes parameters as a timeout is triggered.
 */
public interface TimeoutExpirationContext {
    /**
     * Gets a map of headers and values at the point the timeout
     * has been originally requested.
     */
    Map<HeaderName<?>, Object> getOriginalHeaders();
}

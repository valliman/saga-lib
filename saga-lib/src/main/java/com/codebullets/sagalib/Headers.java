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

package com.codebullets.sagalib;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class when working with headers.
 */
public final class Headers {
    private Headers() { }

    /**
     * Creates a new map, by picking all header entries from the
     * provided stream.
     */
    public static Map<HeaderName<?>, Object> copyFromStream(@Nullable final Stream<Map.Entry<HeaderName<?>, Object>> stream) {
        Map<HeaderName<?>, Object> headerMap;

        if (stream == null) {
            headerMap = new HashMap<>();
        } else {
            headerMap = stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        return headerMap;
    }
}

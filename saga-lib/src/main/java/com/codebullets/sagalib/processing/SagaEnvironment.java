/*
 * Copyright 2014 Stefan Domnanovits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codebullets.sagalib.processing;

import com.codebullets.sagalib.SagaLifetimeInterceptor;
import com.codebullets.sagalib.SagaModule;
import com.codebullets.sagalib.context.CurrentExecutionContext;
import com.codebullets.sagalib.storage.StateStorage;
import com.codebullets.sagalib.timeout.TimeoutManager;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Set;

/**
 * Collects saga environment instances used during saga execution.
 */
public final class SagaEnvironment {
    private final TimeoutManager timeoutManager;
    private final StateStorage storage;
    private final SagaFactory sagaFactory;
    private final Provider<CurrentExecutionContext> contextProvider;
    private final Iterable<SagaModule> modules;
    private final Iterable<SagaLifetimeInterceptor> interceptors;

    /**
     * Generates a new instance of SagaEnvironment.
     */
    @Inject
    public SagaEnvironment(
            final TimeoutManager timeoutManager,
            final StateStorage storage,
            final SagaFactory sagaFactory,
            final Provider<CurrentExecutionContext> contextProvider,
            final Set<SagaModule> modules,
            final Set<SagaLifetimeInterceptor> interceptors) {
        this.timeoutManager = timeoutManager;
        this.storage = storage;
        this.sagaFactory = sagaFactory;
        this.contextProvider = contextProvider;
        this.modules = modules;
        this.interceptors = interceptors;
    }

    /**
     * Gets the timeout manager.
     */
    public TimeoutManager timeoutManager() {
        return timeoutManager;
    }

    /**
     * Gets the state storage.
     */
    public StateStorage storage() {
        return storage;
    }

    /**
     * Gets the saga factory.
     */
    public SagaFactory sagaFactory() {
        return sagaFactory;
    }

    /**
     * Gets the list of known saga modules.
     */
    public Iterable<SagaModule> modules() {
        return modules;
    }

    /**
     * Gets the list of known interceptors.
     */
    public Iterable<SagaLifetimeInterceptor> interceptors() {
        return interceptors;
    }

    /**
     * Gets the execution context provider.
     */
    public Provider<CurrentExecutionContext> contextProvider() {
        return contextProvider;
    }

    /**
     * Creates a new SagaEnvironment instance.
     */
    public static SagaEnvironment create(
            final TimeoutManager timeoutManager,
            final StateStorage storage,
            final SagaFactory sagaFactory,
            final Provider<CurrentExecutionContext> contextProvider,
            final Set<SagaModule> modules,
            final Set<SagaLifetimeInterceptor> interceptors) {
        return new SagaEnvironment(timeoutManager, storage, sagaFactory, contextProvider, modules, interceptors);
    }
}
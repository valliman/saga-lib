/*
 * Copyright 2013 Stefan Domnanovits
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

import com.codebullets.sagalib.MessageStream;
import com.codebullets.sagalib.timeout.Timeout;
import com.codebullets.sagalib.timeout.TimeoutExpired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Controls the saga message flow.
 */
public class SagaMessageStream implements MessageStream {
    private static final Logger LOG = LoggerFactory.getLogger(SagaFactory.class);
    private static final Map<String, Object> EMPTY_HEADERS = Collections.EMPTY_MAP;

    private final SagaEnvironment environment;
    private final HandlerInvoker invoker;
    private final Executor executorService;

    /**
     * Creates a new SagaMessageStream instance.
     */
    @Inject
    public SagaMessageStream(
            final HandlerInvoker invoker,
            final SagaEnvironment environment,
            final Executor executorService) {
        this.executorService = executorService;
        this.environment = environment;
        this.invoker = invoker;

        environment.timeoutManager().addExpiredCallback(new TimeoutExpired() {
            @Override
            public void expired(final Timeout timeout) {
                timeoutHasExpired(timeout);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@Nonnull final Object message) {
        checkNotNull(message, "Message to handle must not be null.");

        SagaExecutionTask task = createExecutor(message, EMPTY_HEADERS);
        executorService.execute(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@Nonnull final Object message, final Map<String, Object> headers) {
        checkNotNull(message, "Message to handle must not be null.");

        SagaExecutionTask task = createExecutor(message, headers);
        executorService.execute(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(@Nonnull final Object message) throws InvocationTargetException, IllegalAccessException {
        checkNotNull(message, "Message to handle must not be null.");

        SagaExecutionTask executor = createExecutor(message, EMPTY_HEADERS);
        executor.handle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(@Nonnull final Object message, final Map<String, Object> headers) throws InvocationTargetException, IllegalAccessException {
        checkNotNull(message, "Message to handle must not be null.");

        SagaExecutionTask executor = createExecutor(message, headers != null ? headers : EMPTY_HEADERS);
        executor.handle();
    }

    /**
     * Called whenever the timeout manager reports an expired timeout.
     */
    private void timeoutHasExpired(final Timeout timeout) {
        try {
            handle(timeout);
        } catch (Exception ex) {
            LOG.error("Error handling timeout {}", timeout, ex);
        }
    }

    private SagaExecutionTask createExecutor(final Object message, final Map<String, Object> headers) {
        return new SagaExecutionTask(environment, invoker, message, headers);
    }
}

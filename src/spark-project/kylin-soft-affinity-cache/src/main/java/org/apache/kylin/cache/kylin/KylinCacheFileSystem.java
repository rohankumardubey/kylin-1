/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kylin.cache.kylin;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.TaskContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kylin.cache.fs.AbstractCacheFileSystem;
import org.apache.kylin.cache.fs.CacheFileSystemConstants;

public class KylinCacheFileSystem extends AbstractCacheFileSystem {

    private static final Logger LOG = LoggerFactory.getLogger(KylinCacheFileSystem.class);

    /**
     * Check whether it needs to cache data on the current executor
     */
    @Override
    public boolean isUseLocalCacheForTargetExecs() {
        if (null == TaskContext.get()) {
            LOG.warn("Task Context is null.");
            return false;
        }
        String localCacheForCurrExecutor = TaskContext.get()
                .getLocalProperty(CacheFileSystemConstants.PARAMS_KEY_LOCAL_CACHE_FOR_CURRENT_FILES);
        LOG.info("Cache for current executor is {}", localCacheForCurrExecutor);
        return (StringUtils.isNotBlank(localCacheForCurrExecutor) && Boolean.parseBoolean(localCacheForCurrExecutor));
    }
}

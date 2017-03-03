/**
 * Copyright 2017 Gitana Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more information, please contact Gitana Software, Inc. at this
 * address:
 *
 *   info@cloudcms.com
 */

package org.gitana.platform.client.repository;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.gitana.platform.client.platform.AbstractPlatformDocumentImpl;

/**
 * @author uzi
 */
public abstract class AbstractRepositoryDocumentImpl extends AbstractPlatformDocumentImpl implements RepositoryDocument
{
    private Repository repository;

    protected AbstractRepositoryDocumentImpl(Repository repository, ObjectNode obj, boolean isSaved)
    {
    	super(repository.getPlatform(), obj, isSaved);

        this.repository = repository;
    }

    @Override
    public Repository getRepository()
    {
        return this.repository;
    }

    @Override
    public String getRepositoryId()
    {
        return getRepository().getId();
    }

}

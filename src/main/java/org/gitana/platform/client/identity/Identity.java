/**
 * Copyright 2010 Gitana Software, Inc.
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
 *   info@gitanasoftware.com
 */

package org.gitana.platform.client.identity;

import org.codehaus.jackson.node.ObjectNode;
import org.gitana.platform.client.directory.DirectoryDocument;
import org.gitana.platform.client.registrar.Registrar;
import org.gitana.platform.client.support.Selfable;
import org.gitana.platform.support.ResultMap;

/**
 * @author uzi
 */
public interface Identity extends DirectoryDocument, Selfable
{
    /**
     * Changes the password for this identity.
     *
     * @param password
     * @param verifyPassword
     */
    public void changePassword(String password, String verifyPassword);

    /**
     * @return all of the user objects that this identity has on any platform
     */
    public ResultMap<ObjectNode> findUserObjects();

    /**
     * Finds the first user object that this identity has on the given tenant's platform.
     *
     * @param tenantId
     *
     * @return user object
     */
    public ObjectNode findUserObjectForTenant(String tenantId);

    /**
     * @return all of the tenant objects that this identity's users participate in
     */
    public ResultMap<ObjectNode> findTenantObjects();

    /**
     * Finds all of the tenant objects that this identity's users participate in.
     * Filters and keeps only those within the specified registry.
     *
     * @param registrar
     * @return
     */
    public ResultMap<ObjectNode> findTenantObjects(Registrar registrar);

    /**
     * Finds all of the tenant objects that this identity's users participate in.
     * Filters and keeps only those tenants whose user has the specified authority against the tenant platform.
     *
     * @param authorityId
     * @return
     */
    public ResultMap<ObjectNode> findTenantObjects(String authorityId);

    /**
     * Finds all of the tenant objects that this identity's users participate in.
     * Filters and keeps only those within the specified registry.
     * Keeps only those tenants whose user has the specified authority against the tenant platform.
     *
     * @param registrar
     * @return
     */
    public ResultMap<ObjectNode> findTenantObjects(Registrar registrar, String authorityId);

}
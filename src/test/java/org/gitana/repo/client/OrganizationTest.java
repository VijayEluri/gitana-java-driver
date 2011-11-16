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

package org.gitana.repo.client;

import org.codehaus.jackson.node.ObjectNode;
import org.gitana.repo.query.QueryBuilder;
import org.gitana.repo.support.ResultMap;
import org.junit.Test;

/**
 * @author uzi
 */
public class OrganizationTest extends AbstractTestCase
{
    @Test
    public void testCRUD()
    {
        Gitana gitana = new Gitana();

        // authenticate
        Server server = gitana.authenticate("admin", "admin");

        // create an organization
        String title = "organization1-" + System.currentTimeMillis();
        Organization organization1 = server.createOrganization();
        organization1.setTitle(title);
        organization1.update();

        // validate
        ResultMap<Organization> organizations = server.listOrganizations();
        assertTrue(organizations.size() > 0);
        assertNotNull(organizations.get(organization1.getId()));

        // query
        ObjectNode query = QueryBuilder.start("title").is(title).get();
        organizations = server.queryOrganizations(query);
        assertEquals(1, organizations.size());

        // delete
        organization1.delete();
        organizations = server.queryOrganizations(query);
        assertEquals(0, organizations.size());
    }

}
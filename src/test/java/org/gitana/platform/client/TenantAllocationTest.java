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

package org.gitana.platform.client;

import org.codehaus.jackson.node.ObjectNode;
import org.gitana.platform.client.api.Client;
import org.gitana.platform.client.domain.Domain;
import org.gitana.platform.client.platform.Platform;
import org.gitana.platform.client.principal.DomainUser;
import org.gitana.platform.client.registrar.Registrar;
import org.gitana.platform.client.tenant.Tenant;
import org.gitana.util.JsonUtil;
import org.junit.Test;

/**
 * @author uzi
 */
public class TenantAllocationTest extends AbstractTestCase
{
    @Test
    public void testAllocations()
        throws Exception
    {
        Gitana gitana = new Gitana();

        // authenticate
        Platform platform = gitana.authenticate("admin", "admin");

        // default registrar
        Registrar registrar = platform.readRegistrar("default");

        // create a domain
        Domain domain = platform.createDomain();

        // create a principal + tenant (#1)
        String userName1 = "user1-" + System.currentTimeMillis();
        DomainUser user1 = domain.createUser(userName1, "pw");
        Tenant tenant1 = registrar.createTenant(user1, "unlimited");
        ObjectNode defaultClientObject1 = tenant1.readDefaultAllocatedClientObject();
        assertNotNull(defaultClientObject1);
        String clientKey1 = JsonUtil.objectGetString(defaultClientObject1, Client.FIELD_KEY);
        String clientSecret1 = JsonUtil.objectGetString(defaultClientObject1, Client.FIELD_SECRET);

        //
        // now authenticate as the tenant principal #1
        //

        gitana = new Gitana(clientKey1, clientSecret1);
        platform = gitana.authenticateOnTenant(user1, "pw", tenant1);

        // now we create 21 things
        //
        //   6 applications
        //   5 repositories
        //   4 domains
        //   3 vaults
        //   2 consumers
        //   1 registrar

        // as we create these, they should be automatically allocated to our tenant

        platform.createApplication();
        platform.createApplication();
        platform.createApplication();
        platform.createApplication();
        platform.createApplication();
        platform.createApplication();
        platform.createRepository();
        platform.createRepository();
        platform.createRepository();
        platform.createRepository();
        platform.createRepository();
        platform.createDomain();
        platform.createDomain();
        platform.createDomain();
        platform.createDomain();
        platform.createVault();
        platform.createVault();
        platform.createVault();
        platform.createClient();
        platform.createClient();
        platform.createRegistrar();

        // validate via general queries
        assertEquals(6+1, platform.listApplications().size()); // 1 default
        assertEquals(5, platform.listRepositories().size());
        assertEquals(4+1, platform.listDomains().size()); // 1 custom, 1 default
        assertEquals(3, platform.listVaults().size());
        assertEquals(2+1, platform.listClients().size()); // 2 custom, 1 default
        assertEquals(1, platform.listRegistrars().size());




        //
        // now authenticate again as the admin
        //

        platform = new Gitana().authenticate("admin", "admin");
        
        tenant1 = platform.readRegistrar(registrar.getId()).readTenant(tenant1.getId());
        

        // now check allocations for the tenant object
        assertEquals(24, tenant1.listAllocatedObjects().size()); // 21 + 3 defaults (domain, client, application) // and then -1 for some reason, cannot find a third client?
        assertEquals(6+1, tenant1.listAllocatedApplicationObjects().size()); // 7
        assertEquals(5, tenant1.listAllocatedRepositoryObjects().size()); // 5
        assertEquals(4+1, tenant1.listAllocatedDomainObjects().size()); // 5
        assertEquals(3, tenant1.listAllocatedVaultObjects().size()); // 3
        assertEquals(2+1, tenant1.listAllocatedClientObjects().size()); // 3
        assertEquals(1, tenant1.listAllocatedRegistrarObjects().size()); // 1
    }

}
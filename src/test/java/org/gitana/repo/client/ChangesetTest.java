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

import org.gitana.repo.client.nodes.BaseNode;
import org.gitana.repo.client.nodes.Node;
import org.gitana.repo.support.Pagination;
import org.gitana.repo.support.ResultMap;
import org.gitana.util.JsonUtil;
import org.junit.Test;

/**
 * @author uzi
 */
public class ChangesetTest extends AbstractTestCase
{
    @Test
    public void testChangesets1()
    {
        Gitana gitana = new Gitana();

        // authenticate
        Server server = gitana.authenticate("admin", "admin");

        // create a repo
        Repository repository = server.createRepository();

        // get the master branch
        Branch branch = repository.readBranch("master"); // 0

        // create some nodes
        Node n1 = (Node) branch.createNode(); // 1
        Node n2 = (Node) branch.createNode(); // 2
        assertNotSame(n1.getChangesetId(), n2.getChangesetId());
        Node n3 = (Node) branch.createNode(); // 3
        assertNotSame(n2.getChangesetId(), n3.getChangesetId());
        Node n4 = (Node) branch.createNode(); // 4
        assertNotSame(n3.getChangesetId(), n4.getChangesetId());
        Node n5 = (Node) branch.createNode(); // 5
        assertNotSame(n4.getChangesetId(), n5.getChangesetId());
        Node n6 = (Node) branch.createNode(); // 6
        assertNotSame(n5.getChangesetId(), n6.getChangesetId());
        Node n7 = (Node) branch.createNode(); // 7
        assertNotSame(n6.getChangesetId(), n7.getChangesetId());
        Node n8 = (Node) branch.createNode(); // 8
        assertNotSame(n7.getChangesetId(), n8.getChangesetId());

        // list all of the changesets (should be 9 - 8 created + 0:root)
        assertEquals(9, repository.listChangesets().size());

        // fetch changesets
        assertEquals(9, repository.fetchChangesets().size());

        // assert paginated lists work
        Pagination pagination = new Pagination();
        pagination.setLimit(3);
        assertEquals(3, repository.listChangesets(pagination).size());

        // query
        assertEquals(9, repository.queryChangesets(JsonUtil.createObject()).size());

        // assert paginated queries work
        assertEquals(3, repository.queryChangesets(JsonUtil.createObject(), pagination).size());

        // check one of the changesets and make sure we can snag nodes off it
        Changeset changeset6 = repository.fetchChangesets().get(n6.getChangesetId());
        ResultMap<BaseNode> nodes6 = changeset6.listNodes();
        assertTrue(nodes6.get(n6.getId()) != null);
    }
}
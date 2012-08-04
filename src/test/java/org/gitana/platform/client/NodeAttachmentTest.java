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

import org.gitana.platform.client.attachment.Attachment;
import org.gitana.platform.client.branch.Branch;
import org.gitana.platform.client.node.Node;
import org.gitana.platform.client.platform.Platform;
import org.gitana.platform.client.repository.Repository;
import org.gitana.platform.support.ResultMap;
import org.gitana.util.ClasspathUtil;
import org.junit.Test;

/**
 * @author uzi
 */
public class NodeAttachmentTest extends AbstractTestCase
{
    @Test
    public void testAttachments()
        throws Exception
    {
        Gitana gitana = new Gitana();

        // authenticate
        Platform platform = gitana.authenticate("admin", "admin");

        // create a repository
        Repository repo = platform.createRepository();

        // master branch
        Branch master = repo.readBranch("master");

        // create a node
        Node node = (Node) master.createNode();

        // upload an attachment
        String filename = "testfilename-" + System.currentTimeMillis() + "-bugs.jpeg";
        byte[] bytes = ClasspathUtil.bytesFromClasspath("org/gitana/platform/client/bugs.jpeg");
        node.uploadAttachment("attach1", bytes, "image/jpeg");
        node.uploadAttachment("attach2", bytes, "image/jpeg");
        node.uploadAttachment("attach3", bytes, "image/jpeg");

        // list attachment
        ResultMap<Attachment> attachments = node.listAttachments();
        assertTrue(attachments.size() == 3);

        assertNotNull(attachments.get("attach1"));
        assertNotNull(attachments.get("attach2"));
        assertNotNull(attachments.get("attach3"));

        assertEquals("image/jpeg", attachments.get("attach1").getContentType());
        assertEquals(bytes.length, attachments.get("attach1").getLength());
    }
}

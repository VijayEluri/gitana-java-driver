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

package org.gitana.platform.client.datastore;

import org.gitana.platform.client.cluster.Cluster;
import org.gitana.platform.client.document.Document;
import org.gitana.platform.client.support.AccessControllable;
import org.gitana.platform.client.support.Selfable;
import org.gitana.platform.client.team.Teamable;

/**
 * @author uzi
 */
public interface DataStore extends Document, AccessControllable, Selfable, Teamable
{
    /**
     * @return the data store id
     */
    public String getId();

    /**
     * @return the data store type
     */
    public String getType();
}
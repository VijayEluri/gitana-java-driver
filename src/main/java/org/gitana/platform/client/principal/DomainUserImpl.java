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

package org.gitana.platform.client.principal;

import org.codehaus.jackson.node.ObjectNode;
import org.gitana.platform.client.branch.Branch;
import org.gitana.platform.client.domain.Domain;
import org.gitana.platform.client.types.Person;
import org.gitana.platform.services.principals.PrincipalType;
import org.gitana.util.JsonUtil;

/**
 * @author uzi
 */
public class DomainUserImpl extends AbstractDomainPrincipalImpl implements DomainUser
{
    public DomainUserImpl(Domain domain, ObjectNode obj, boolean isSaved)
    {
    	super(domain, obj, isSaved);

        init();
    }

    /**
     * Sets the principal type to USER
     */
    protected void init()
    {
        this.setType(PrincipalType.USER);
    }

    @Override
    public void changePassword(String newPassword)
    {
        ObjectNode object = JsonUtil.createObject();
        object.put("password", newPassword);

        getRemote().post(getResourceUri() + "/changepassword", object);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // ACCESSORS
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String getFirstName()
    {
        return getString(FIELD_FIRST_NAME);
    }

    @Override
    public void setFirstName(String firstName)
    {
        set(FIELD_FIRST_NAME, firstName);
    }

    @Override
    public String getLastName()
    {
        return getString(FIELD_LAST_NAME);
    }

    @Override
    public void setLastName(String lastName)
    {
        set(FIELD_LAST_NAME,  lastName);
    }

    @Override
    public String getCompanyName()
    {
        return getString(FIELD_COMPANY_NAME);
    }

    @Override
    public void setCompanyName(String companyName)
    {
        set(FIELD_COMPANY_NAME, companyName);
    }

    @Override
    public String getEmail()
    {
        return getString(FIELD_EMAIL);
    }

    @Override
    public void setEmail(String email)
    {
        set(FIELD_EMAIL, email);
    }

    @Override
    public Person readPerson(Branch branch)
    {
        return readPerson(branch, false);
    }

    @Override
    public Person readPerson(Branch branch, boolean createIfNotFound)
    {
        return branch.readPerson(getName(), createIfNotFound);
    }
}

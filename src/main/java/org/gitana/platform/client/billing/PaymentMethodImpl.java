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

package org.gitana.platform.client.billing;

import org.codehaus.jackson.node.ObjectNode;
import org.gitana.platform.client.Driver;
import org.gitana.platform.client.support.DriverContext;
import org.gitana.platform.client.support.Remote;
import org.gitana.platform.support.GitanaObjectImpl;

/**
 * @author uzi
 */
public class PaymentMethodImpl extends GitanaObjectImpl implements PaymentMethod
{
    private Billing billing = null;
    
    public PaymentMethodImpl(Billing billing, ObjectNode obj)
    {
        super(obj);

        this.billing = billing;
    }

    protected Driver getDriver()
    {
        return DriverContext.getDriver();
    }

    protected Remote getRemote()
    {
        return getDriver().getRemote();
    }

    protected Billing getBilling()
    {
        return this.billing;
    }

    @Override
    public String getId()
    {
        return getString(FIELD_ID);
    }

    protected String getResourceUri()
    {
        return "/billing/paymentmethods/" + getId();
    }

    @Override
    public boolean equals(Object object)
    {
        boolean equals = false;

        if (object instanceof PaymentMethod)
        {
            PaymentMethod other = (PaymentMethod) object;

            equals = (this.getId().equals(other.getId()));
        }

        return equals;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SELFABLE
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void update()
    {
        getRemote().put(getResourceUri(), getObject());
    }

    @Override
    public void delete()
    {
        getRemote().delete(getResourceUri());
    }

    @Override
    public void reload()
    {
        PaymentMethod paymentMethod = getBilling().readPaymentMethod(getId());

        // clear our own object and push new properties
        this.getObject().removeAll();
        this.getObject().putAll(paymentMethod.getObject());
    }

    @Override
    public int getExpirationMonth() 
    {
        return getInt(FIELD_EXPIRATION_MONTH);
    }

    @Override
    public void setExpirationMonth(int expirationMonth) 
    {
        set(FIELD_EXPIRATION_MONTH, expirationMonth);
    }

    @Override
    public int getExpirationYear() 
    {
        return getInt(FIELD_EXPIRATION_YEAR);
    }

    @Override
    public void setExpirationYear(int expirationYear) 
    {
        set(FIELD_EXPIRATION_YEAR, expirationYear);
    }

    @Override
    public String getHolderName() 
    {
        return getString(FIELD_HOLDER_NAME);
    }

    @Override
    public void setHolderName(String holderName) 
    {
        set(FIELD_HOLDER_NAME, holderName);
    }

    @Override
    public String getCardNumber() 
    {
        return getString(FIELD_CARDNUMBER);
    }

    @Override
    public void setCardNumber(String cardNumber) 
    {
        set(FIELD_CARDNUMBER, cardNumber);
    }
}

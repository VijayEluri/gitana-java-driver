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
import org.gitana.platform.client.billing.BillingProviderConfiguration;
import org.gitana.platform.client.billing.BillingTransaction;
import org.gitana.platform.client.billing.PaymentMethod;
import org.gitana.platform.client.domain.Domain;
import org.gitana.platform.client.plan.Plan;
import org.gitana.platform.client.platform.Platform;
import org.gitana.platform.client.principal.DomainUser;
import org.gitana.platform.client.registrar.Registrar;
import org.gitana.platform.client.tenant.Tenant;
import org.gitana.platform.services.payment.BillingSchedule;
import org.gitana.platform.services.plan.DataUnit;
import org.gitana.platform.support.ResultMap;
import org.gitana.util.ClasspathUtil;
import org.gitana.util.JsonUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author uzi
 */
@Ignore
public class BillingTest extends AbstractTestCase
{
    public final static String NUMBER_VISA_GOOD = "4012000033330026";
    public final static String NUMBER_VISA_BAD = "4111111111111115";

    @Test
    @Ignore
    public void testSuccessCase1()
        throws Exception
    {
        // authenticate as "admin"
        Gitana gitana = new Gitana();
        Platform platform = gitana.authenticate("admin", "admin");

        // create using a valid CC
        Tenant tenant1 = createTenant(platform, NUMBER_VISA_GOOD);
        assertNotNull(tenant1);

        // check transactions (should be 1)
        ResultMap<BillingTransaction> transactions = tenant1.listBillingTransactions();
        assertEquals(1, transactions.size());
        
        // grab the transaction and generate a receipt
        BillingTransaction billingTransaction = transactions.values().iterator().next();
        String template = ClasspathUtil.loadFromClasspath("org/gitana/platform/client/billingTransactionReceipt.ftl");
        byte[] data = billingTransaction.generateReceipt(template);
        assertNotNull(data);
        assertTrue(data.length > 0);
    }

    @Test
    @Ignore
    public void testFailureCase1()
        throws Exception
    {
        // authenticate as "admin"
        Gitana gitana = new Gitana();
        Platform platform = gitana.authenticate("admin", "admin");

        // create using an invalid CC
        Exception ex1 = null;
        Tenant tenant1 = null;
        try
        {
            tenant1 = createTenant(platform, NUMBER_VISA_BAD);
        }
        catch (Exception ex)
        {
            ex1 = ex;
        }
        assertNotNull(ex1);
        assertNull(tenant1);
    }
    
    
    private Tenant createTenant(Platform platform, String creditCardNumber)
    {
        // get the "bt" billing provider configuration
        BillingProviderConfiguration bt = platform.readBillingProviderConfiguration("bt");
        
        // create registrar + domain
        ObjectNode registrarObject = JsonUtil.createObject();
        registrarObject.put(Registrar.FIELD_BILLING_PROVIDER_CONFIGURATION_ID, bt.getKey());
        Registrar registrar = platform.createRegistrar(registrarObject);
        Domain domain = platform.createDomain();
        
        // create a plan
        Plan plan = registrar.createPlan("booya");
        plan.setBillingPrice(new BigDecimal("999"));
        plan.setBillingSchedule(BillingSchedule.MONTHLY);
        plan.setCollaboratorAmount(10);
        plan.setDatastoreAmount(10);
        plan.setObjectAmount(10000);
        plan.setRequiresBilling(true);
        plan.setStorageAmount(1);
        plan.setStorageUnit(DataUnit.GB);
        plan.setStorageOveragePrice(new BigDecimal(0.15));
        plan.setStorageOverageUnit(DataUnit.GB);
        plan.setTransferAmount(1);
        plan.setTransferUnit(DataUnit.GB);
        plan.setTransferOveragePrice(new BigDecimal(0.15));
        plan.setTransferOverageUnit(DataUnit.GB);
        plan.update();

        // create a new principal
        String userName = "user-" + System.currentTimeMillis();
        DomainUser user = domain.createUser(userName, "pw");

        // define a payment method
        ObjectNode paymentMethodObject = JsonUtil.createObject();
        paymentMethodObject.put(PaymentMethod.FIELD_NUMBER, creditCardNumber);
        paymentMethodObject.put(PaymentMethod.FIELD_HOLDER_NAME, "Bruce Springsteen");
        paymentMethodObject.put(PaymentMethod.FIELD_EXPIRATION_MONTH, 12);
        paymentMethodObject.put(PaymentMethod.FIELD_EXPIRATION_YEAR, 2015);

        // create a new tenant
        // this should succeed if the credit card successfully billed
        // if there was a validation error with the credit card or the credit card could not process
        // for any reason, we should get an error back
        ObjectNode tenantObject = JsonUtil.createObject();
        Tenant tenant = registrar.createTenant(user, "booya", tenantObject, paymentMethodObject);

        /*
        // authenticate on the new tenant as the new principal
        ObjectNode defaultClientObject = tenant.readDefaultAllocatedClientObject();
        assertNotNull(defaultClientObject);
        String clientKey = JsonUtil.objectGetString(defaultClientObject, Client.FIELD_KEY);
        String clientSecret = JsonUtil.objectGetString(defaultClientObject, Client.FIELD_SECRET);
        gitana = new Gitana(clientKey, clientSecret);
        platform = gitana.authenticateOnTenant(user, "pw", tenant);
        */
        
        return tenant;
    }

}
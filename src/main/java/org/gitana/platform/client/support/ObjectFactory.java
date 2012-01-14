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

package org.gitana.platform.client.support;

import org.codehaus.jackson.node.ObjectNode;
import org.gitana.platform.client.api.Consumer;
import org.gitana.platform.client.application.Application;
import org.gitana.platform.client.archive.Archive;
import org.gitana.platform.client.attachment.Attachable;
import org.gitana.platform.client.attachment.Attachment;
import org.gitana.platform.client.branch.Branch;
import org.gitana.platform.client.changeset.Changeset;
import org.gitana.platform.client.cluster.Cluster;
import org.gitana.platform.client.domain.Domain;
import org.gitana.platform.client.job.Job;
import org.gitana.platform.client.log.LogEntry;
import org.gitana.platform.client.nodes.Association;
import org.gitana.platform.client.nodes.BaseNode;
import org.gitana.platform.client.plan.Plan;
import org.gitana.platform.client.platform.Platform;
import org.gitana.platform.client.platform.PlatformDataStore;
import org.gitana.platform.client.principal.DomainPrincipal;
import org.gitana.platform.client.registrar.Registrar;
import org.gitana.platform.client.repository.Repository;
import org.gitana.platform.client.stack.Stack;
import org.gitana.platform.client.team.Team;
import org.gitana.platform.client.team.Teamable;
import org.gitana.platform.client.tenant.Tenant;
import org.gitana.platform.client.vault.Vault;
import org.gitana.platform.services.authority.AuthorityGrant;
import org.gitana.platform.support.QName;
import org.gitana.platform.support.ResultMap;

import java.util.Map;

/**
 * @author uzi
 */
public interface ObjectFactory
{
    public Platform platform(Cluster cluster, Response response);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // REPOSITORIES
    //

    public Repository repository(Platform platform);
    public Repository repository(Platform platform, ObjectNode object);
    public Repository repository(Platform platform, Response response);
    public ResultMap<Repository> repositories(Platform platform, Response response);

    public Branch branch(Repository repository);
    public Branch branch(Repository repository, ObjectNode object);
    public Branch branch(Repository repository, Response response);
    public ResultMap<Branch> branches(Repository repository, Response response);

    public Changeset changeset(Repository repository, Response response);
    public ResultMap<Changeset> changesets(Repository repository, Response response);

    // in-memory
    public BaseNode node(Branch branch, QName typeQName);
    public BaseNode node(Branch branch, QName typeQName, ObjectNode object);
    public BaseNode node(Branch branch, Response response);
    public ResultMap<BaseNode> nodes(Branch branch, Response response);

    public Association association(Branch branch, QName typeQName);
    public Association association(Branch branch, QName typeQName, ObjectNode object);
    public Association association(Branch branch, Response response);
    public ResultMap<Association> associations(Branch branch, Response response);

    // dynamic node registry
    public void register(QName typeQName, Class implementationClass);

    // produces a node (raw function)
    public BaseNode produce(Branch branch, ObjectNode object, boolean isSaved);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // DOMAINS
    //

    public Domain domain(Platform platform);
    public Domain domain(Platform platform, ObjectNode object);
    public Domain domain(Platform platform, Response response);
    public ResultMap<Domain> domains(Platform platform, Response response);

    public DomainPrincipal domainPrincipal(Platform platform, ObjectNode object);
    public DomainPrincipal domainPrincipal(Platform platform, Response response);
    public ResultMap<DomainPrincipal> domainPrincipals(Platform platform, Response response);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // PLATFORM
    //

    // stacks
    public Stack stack(Platform platform, Response response);
    public ResultMap<Stack> stacks(Platform platform, Response response);

    // consumers
    public Consumer consumer(Platform platform, ObjectNode object);
    public Consumer consumer(Platform platform, Response response);
    public ResultMap<Consumer> consumers(Platform platform, Response response);




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CLUSTER
    //

    // jobs
    public Job job(Cluster cluster, Response response);
    public ResultMap<Job> jobs(Cluster cluster, Response response);

    // log entries
    public LogEntry logEntry(Cluster cluster, Response response);
    public ResultMap<LogEntry> logEntries(Cluster cluster, Response response);




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // VAULT
    //

    public Vault vault(Platform platform);
    public Vault vault(Platform platform, ObjectNode object);
    public Vault vault(Platform platform, Response response);
    public ResultMap<Vault> vaults(Platform platform, Response response);

    // archives
    public Archive archive(Vault vault, Response response);
    public ResultMap<Archive> archives(Vault vault, Response response);



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // VAULT
    //

    public Application application(Platform platform);
    public Application application(Platform platform, ObjectNode object);
    public Application application(Platform platform, Response response);
    public ResultMap<Application> applications(Platform platform, Response response);




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // MISCELLANEOUS
    //

    // attachments
    public ResultMap<Attachment> attachments(Attachable attachable, Response response);
    public Map<String, Map<String, AuthorityGrant>> principalAuthorityGrants(Response response);

    // teams
    public Team team(Cluster cluster, Teamable teamable, String teamKey, Response response);
    public ResultMap<Team> teams(Cluster cluster, Teamable teamable, Response response);



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // VAULT
    //

    public Registrar registrar(Platform platform);
    public Registrar registrar(Platform platform, ObjectNode object);
    public Registrar registrar(Platform platform, Response response);
    public ResultMap<Registrar> registrars(Platform platform, Response response);

    // tenants
    public Tenant tenant(Registrar management, ObjectNode object);
    public Tenant tenant(Registrar management, Response response);
    public ResultMap<Tenant> tenants(Registrar management, Response response);

    // plans
    public Plan plan(Registrar management, ObjectNode object);
    public Plan plan(Registrar management, Response response);
    public ResultMap<Plan> plans(Registrar management, Response response);




    public PlatformDataStore platformDataStore(Platform platform, ObjectNode object);
    public ResultMap<PlatformDataStore> platformDataStores(Platform platform, Response response);

}
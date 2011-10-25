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
import org.gitana.repo.client.support.JobLogEntry;

import java.util.Calendar;
import java.util.List;

/**
 * @author uzi
 */
public interface Job extends Document
{	
    // fields
    public final static String FIELD_TYPE = "type";
    public final static String FIELD_RUN_AS = "runAs";
    public final static String FIELD_IS_SUBMITTED = "is_submitted";
    public final static String FIELD_IS_STARTED = "is_started";
    public final static String FIELD_IS_RUNNING = "is_running";
    public final static String FIELD_IS_FINISHED = "is_finished";
    public final static String FIELD_IS_ERROR = "is_error";

    public final static String FIELD_SUBMITTED_BY = "submitted_by";

    public final static String FIELD_START_TIMESTAMP = "start_timestamp";
    public final static String FIELD_STARTED_BY = "started_by";

    public final static String FIELD_STOP_TIMESTAMP = "stop_timestamp";
    public final static String FIELD_STOPPED_BY = "stopped_by";

    public final static String FIELD_PRIORITY = "priority";

    public final static String FIELD_ATTEMPTS = "attempts";

    public final static String FIELD_SCHEDULE_START_MS = "schedule_start_ms";

    public final static String FIELD_LOG_ENTRIES = "log_entries";

    public final static String FIELD_CURRENT_THREAD = "current_thread";
    public final static String FIELD_CURRENT_SERVER = "current_server";
    public final static String FIELD_CURRENT_SERVER_TIMESTAMP = "current_server_timestmap";


    // accessors

    public String getType();
    public String getRunAs();
    public boolean isStarted();
    public boolean isRunning();
    public boolean isError();
    public boolean isFinished();
    public boolean isSubmitted();
    public String getStartedBy();
    public String getStoppedBy();
    public String getSubmittedBy();
    public ObjectNode getStartTimestamp();
    public ObjectNode getStopTimestamp();
    public Calendar getStartTime();
    public Calendar getStopTime();

    public List<JobLogEntry> getLogEntries();

    public int getPriority();

    public String getCurrentThreadId();

    public String getCurrentServerId();

    public long getCurrentServerTimestamp();

    public long getScheduleStart();

    public int getAttempts();
}
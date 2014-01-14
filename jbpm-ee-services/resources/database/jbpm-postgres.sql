
    alter table Attachment 
        drop constraint FK_7ndpfa311i50bq7hy18q05va3;

    alter table Attachment 
        drop constraint FK_hqupx569krp0f0sgu9kh87513;

    alter table BooleanExpression 
        drop constraint FK_394nf2qoc0k9ok6omgd6jtpso;

    alter table CorrelationPropertyInfo 
        drop constraint FK_hrmx1m882cejwj9c04ixh50i4;

    alter table Deadline 
        drop constraint FK_68w742sge00vco2cq3jhbvmgx;

    alter table Deadline 
        drop constraint FK_euoohoelbqvv94d8a8rcg8s5n;

    alter table Delegation_delegates 
        drop constraint FK_gn7ula51sk55wj1o1m57guqxb;

    alter table Delegation_delegates 
        drop constraint FK_fajq6kossbsqwr3opkrctxei3;

    alter table Escalation 
        drop constraint FK_ay2gd4fvl9yaapviyxudwuvfg;

    alter table EventTypes 
        drop constraint FK_nrecj4617iwxlc65ij6m7lsl1;

    alter table I18NText 
        drop constraint FK_k16jpgrh67ti9uedf6konsu1p;

    alter table I18NText 
        drop constraint FK_fd9uk6hemv2dx1ojovo7ms3vp;

    alter table I18NText 
        drop constraint FK_4eyfp69ucrron2hr7qx4np2fp;

    alter table I18NText 
        drop constraint FK_pqarjvvnwfjpeyb87yd7m0bfi;

    alter table I18NText 
        drop constraint FK_o84rkh69r47ti8uv4eyj7bmo2;

    alter table I18NText 
        drop constraint FK_g1trxri8w64enudw2t1qahhk5;

    alter table I18NText 
        drop constraint FK_qoce92c70adem3ccb3i7lec8x;

    alter table I18NText 
        drop constraint FK_bw8vmpekejxt1ei2ge26gdsry;

    alter table I18NText 
        drop constraint FK_21qvifarxsvuxeaw5sxwh473w;

    alter table Notification 
        drop constraint FK_bdbeml3768go5im41cgfpyso9;

    alter table Notification_BAs 
        drop constraint FK_mfbsnbrhth4rjhqc2ud338s4i;

    alter table Notification_BAs 
        drop constraint FK_fc0uuy76t2bvxaxqysoo8xts7;

    alter table Notification_Recipients 
        drop constraint FK_blf9jsrumtrthdaqnpwxt25eu;

    alter table Notification_Recipients 
        drop constraint FK_3l244pj8sh78vtn9imaymrg47;

    alter table Notification_email_header 
        drop constraint FK_ptaka5kost68h7l3wflv7w6y8;

    alter table Notification_email_header 
        drop constraint FK_eth4nvxn21fk1vnju85vkjrai;

    alter table PeopleAssignments_BAs 
        drop constraint FK_t38xbkrq6cppifnxequhvjsl2;

    alter table PeopleAssignments_BAs 
        drop constraint FK_omjg5qh7uv8e9bolbaq7hv6oh;

    alter table PeopleAssignments_ExclOwners 
        drop constraint FK_pth28a73rj6bxtlfc69kmqo0a;

    alter table PeopleAssignments_ExclOwners 
        drop constraint FK_b8owuxfrdng050ugpk0pdowa7;

    alter table PeopleAssignments_PotOwners 
        drop constraint FK_tee3ftir7xs6eo3fdvi3xw026;

    alter table PeopleAssignments_PotOwners 
        drop constraint FK_4dv2oji7pr35ru0w45trix02x;

    alter table PeopleAssignments_Recipients 
        drop constraint FK_4g7y3wx6gnokf6vycgpxs83d6;

    alter table PeopleAssignments_Recipients 
        drop constraint FK_enhk831fghf6akjilfn58okl4;

    alter table PeopleAssignments_Stakeholders 
        drop constraint FK_met63inaep6cq4ofb3nnxi4tm;

    alter table PeopleAssignments_Stakeholders 
        drop constraint FK_4bh3ay74x6ql9usunubttfdf1;

    alter table Reassignment 
        drop constraint FK_pnpeue9hs6kx2ep0sp16b6kfd;

    alter table Reassignment_potentialOwners 
        drop constraint FK_8frl6la7tgparlnukhp8xmody;

    alter table Reassignment_potentialOwners 
        drop constraint FK_qbega5ncu6b9yigwlw55aeijn;

    alter table Task 
        drop constraint FK_dpk0f9ucm14c78bsxthh7h8yh;

    alter table Task 
        drop constraint FK_nh9nnt47f3l61qjlyedqt05rf;

    alter table Task 
        drop constraint FK_k02og0u71obf1uxgcdjx9rcjc;

    alter table task_comment 
        drop constraint FK_aax378yjnsmw9kb9vsu994jjv;

    alter table task_comment 
        drop constraint FK_1ws9jdmhtey6mxu7jb0r0ufvs;

    drop table if exists Attachment cascade;

    drop table if exists BAMTaskSummary cascade;

    drop table if exists BooleanExpression cascade;

    drop table if exists Content cascade;

    drop table if exists ContextMappingInfo cascade;

    drop table if exists CorrelationKeyInfo cascade;

    drop table if exists CorrelationPropertyInfo cascade;

    drop table if exists Deadline cascade;

    drop table if exists Delegation_delegates cascade;

    drop table if exists Escalation cascade;

    drop table if exists EventTypes cascade;

    drop table if exists I18NText cascade;

    drop table if exists KieBaseXProcessInstance cascade;

    drop table if exists NodeInstanceLog cascade;

    drop table if exists Notification cascade;

    drop table if exists Notification_BAs cascade;

    drop table if exists Notification_Recipients cascade;

    drop table if exists Notification_email_header cascade;

    drop table if exists OrganizationalEntity cascade;

    drop table if exists PeopleAssignments_BAs cascade;

    drop table if exists PeopleAssignments_ExclOwners cascade;

    drop table if exists PeopleAssignments_PotOwners cascade;

    drop table if exists PeopleAssignments_Recipients cascade;

    drop table if exists PeopleAssignments_Stakeholders cascade;

    drop table if exists PresentationElement cascade;

    drop table if exists ProcessInstanceEventInfo cascade;

    drop table if exists ProcessInstanceInfo cascade;

    drop table if exists ProcessInstanceLog cascade;

    drop table if exists Reassignment cascade;

    drop table if exists Reassignment_potentialOwners cascade;

    drop table if exists SessionInfo cascade;

    drop table if exists Task cascade;

    drop table if exists TaskDef cascade;

    drop table if exists TaskEvent cascade;

    drop table if exists VariableInstanceLog cascade;

    drop table if exists WorkItemInfo cascade;

    drop table if exists email_header cascade;

    drop table if exists task_comment cascade;

    drop sequence ATTACHMENT_ID_SEQ;

    drop sequence BAM_TASK_ID_SEQ;

    drop sequence BOOLEANEXPR_ID_SEQ;

    drop sequence COMMENT_ID_SEQ;

    drop sequence CONTENT_ID_SEQ;

    drop sequence CONTEXT_MAPPING_INFO_ID_SEQ;

    drop sequence CORRELATION_KEY_ID_SEQ;

    drop sequence CORRELATION_PROP_ID_SEQ;

    drop sequence DEADLINE_ID_SEQ;

    drop sequence EMAILNOTIFHEAD_ID_SEQ;

    drop sequence ESCALATION_ID_SEQ;

    drop sequence I18NTEXT_ID_SEQ;

    drop sequence NODE_INST_LOG_ID_SEQ;

    drop sequence NOTIFICATION_ID_SEQ;

    drop sequence PROCESS_INSTANCE_INFO_ID_SEQ;

    drop sequence PROC_INST_EVENT_ID_SEQ;

    drop sequence PROC_INST_LOG_ID_SEQ;

    drop sequence REASSIGNMENT_ID_SEQ;

    drop sequence SESSIONINFO_ID_SEQ;

    drop sequence TASK_DEF_ID_SEQ;

    drop sequence TASK_EVENT_ID_SEQ;

    drop sequence TASK_ID_SEQ;

    drop sequence VAR_INST_LOG_ID_SEQ;

    drop sequence WORKITEMINFO_ID_SEQ;

    drop sequence hibernate_sequence;

    create table Attachment (
        id int8 not null,
        accessType int4,
        attachedAt timestamp,
        attachmentContentId int8 not null,
        contentType varchar(255),
        name varchar(255),
        attachment_size int4,
        attachedBy_id varchar(255),
        TaskData_Attachments_Id int8,
        primary key (id)
    );

    create table BAMTaskSummary (
        pk int8 not null,
        createdDate timestamp,
        duration int8,
        endDate timestamp,
        processInstanceId int8 not null,
        startDate timestamp,
        status varchar(255),
        taskId int8 not null,
        taskName varchar(255),
        userId varchar(255),
        OPTLOCK int4,
        primary key (pk)
    );

    create table BooleanExpression (
        id int8 not null,
        expression text,
        type varchar(255),
        Escalation_Constraints_Id int8,
        primary key (id)
    );

    create table Content (
        id int8 not null,
        content oid,
        primary key (id)
    );

    create table ContextMappingInfo (
        mappingId int8 not null,
        CONTEXT_ID varchar(255) not null,
        KSESSION_ID int4 not null,
        OPTLOCK int4,
        primary key (mappingId)
    );

    create table CorrelationKeyInfo (
        keyId int8 not null,
        name varchar(255),
        processInstanceId int8 not null,
        OPTLOCK int4,
        primary key (keyId)
    );

    create table CorrelationPropertyInfo (
        propertyId int8 not null,
        name varchar(255),
        value varchar(255),
        OPTLOCK int4,
        correlationKey_keyId int8,
        primary key (propertyId)
    );

    create table Deadline (
        id int8 not null,
        deadline_date timestamp,
        escalated int2,
        Deadlines_StartDeadLine_Id int8,
        Deadlines_EndDeadLine_Id int8,
        primary key (id)
    );

    create table Delegation_delegates (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table Escalation (
        id int8 not null,
        name varchar(255),
        Deadline_Escalation_Id int8,
        primary key (id)
    );

    create table EventTypes (
        InstanceId int8 not null,
        element varchar(255)
    );

    create table I18NText (
        id int8 not null,
        language varchar(255),
        shortText varchar(255),
        text text,
        Task_Subjects_Id int8,
        Task_Names_Id int8,
        Task_Descriptions_Id int8,
        Reassignment_Documentation_Id int8,
        Notification_Subjects_Id int8,
        Notification_Names_Id int8,
        Notification_Documentation_Id int8,
        Notification_Descriptions_Id int8,
        Deadline_Documentation_Id int8,
        primary key (id)
    );

    create table KieBaseXProcessInstance (
        kieProcessInstanceId int8 not null,
        releaseArtifactId varchar(255) not null,
        releaseGroupId varchar(255) not null,
        releaseVersion varchar(255) not null,
        OPTLOCK int4,
        primary key (kieProcessInstanceId)
    );

    create table NodeInstanceLog (
        id int8 not null,
        connection varchar(255),
        log_date timestamp,
        externalId varchar(255),
        nodeId varchar(255),
        nodeInstanceId varchar(255),
        nodeName varchar(255),
        nodeType varchar(255),
        processId varchar(255),
        processInstanceId int8 not null,
        type int4 not null,
        workItemId int8,
        primary key (id)
    );

    create table Notification (
        DTYPE varchar(31) not null,
        id int8 not null,
        priority int4 not null,
        Escalation_Notifications_Id int8,
        primary key (id)
    );

    create table Notification_BAs (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table Notification_Recipients (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table Notification_email_header (
        Notification_id int8 not null,
        emailHeaders_id int8 not null,
        mapkey varchar(255) not null,
        primary key (Notification_id, mapkey)
    );

    create table OrganizationalEntity (
        DTYPE varchar(31) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table PeopleAssignments_BAs (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table PeopleAssignments_ExclOwners (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table PeopleAssignments_PotOwners (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table PeopleAssignments_Recipients (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table PeopleAssignments_Stakeholders (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table PresentationElement (
        id int8 not null,
        primary key (id)
    );

    create table ProcessInstanceEventInfo (
        id int8 not null,
        eventType varchar(255),
        processInstanceId int8 not null,
        OPTLOCK int4,
        primary key (id)
    );

    create table ProcessInstanceInfo (
        InstanceId int8 not null,
        lastModificationDate timestamp,
        lastReadDate timestamp,
        processId varchar(255),
        processInstanceByteArray oid,
        startDate timestamp,
        state int4 not null,
        OPTLOCK int4,
        primary key (InstanceId)
    );

    create table ProcessInstanceLog (
        id int8 not null,
        duration int8,
        end_date timestamp,
        externalId varchar(255),
        user_identity varchar(255),
        outcome varchar(255),
        parentProcessInstanceId int8,
        processId varchar(255),
        processInstanceId int8 not null,
        processName varchar(255),
        processVersion varchar(255),
        start_date timestamp,
        status int4,
        primary key (id)
    );

    create table Reassignment (
        id int8 not null,
        Escalation_Reassignments_Id int8,
        primary key (id)
    );

    create table Reassignment_potentialOwners (
        task_id int8 not null,
        entity_id varchar(255) not null
    );

    create table SessionInfo (
        id int4 not null,
        lastModificationDate timestamp,
        rulesByteArray oid,
        startDate timestamp,
        OPTLOCK int4,
        primary key (id)
    );

    create table Task (
        id int8 not null,
        archived int2,
        allowedToDelegate varchar(255),
        formName varchar(255),
        priority int4 not null,
        subTaskStrategy varchar(255),
        activationTime timestamp,
        createdOn timestamp,
        deploymentId varchar(255),
        documentAccessType int4,
        documentContentId int8 not null,
        documentType varchar(255),
        expirationTime timestamp,
        faultAccessType int4,
        faultContentId int8 not null,
        faultName varchar(255),
        faultType varchar(255),
        outputAccessType int4,
        outputContentId int8 not null,
        outputType varchar(255),
        parentId int8 not null,
        previousStatus int4,
        processId varchar(255),
        processInstanceId int8 not null,
        processSessionId int4 not null,
        skipable boolean not null,
        status varchar(255),
        workItemId int8 not null,
        taskType varchar(255),
        OPTLOCK int4,
        taskInitiator_id varchar(255),
        actualOwner_id varchar(255),
        createdBy_id varchar(255),
        primary key (id)
    );

    create table TaskDef (
        id int8 not null,
        name varchar(255),
        priority int4 not null,
        primary key (id)
    );

    create table TaskEvent (
        id int8 not null,
        logTime timestamp,
        taskId int8,
        type varchar(255),
        userId varchar(255),
        OPTLOCK int4,
        primary key (id)
    );

    create table VariableInstanceLog (
        id int8 not null,
        log_date timestamp,
        externalId varchar(255),
        oldValue varchar(255),
        processId varchar(255),
        processInstanceId int8 not null,
        value varchar(255),
        variableId varchar(255),
        variableInstanceId varchar(255),
        primary key (id)
    );

    create table WorkItemInfo (
        workItemId int8 not null,
        creationDate timestamp,
        name varchar(255),
        processInstanceId int8 not null,
        state int8 not null,
        OPTLOCK int4,
        workItemByteArray oid,
        primary key (workItemId)
    );

    create table email_header (
        id int8 not null,
        body text,
        fromAddress varchar(255),
        language varchar(255),
        replyToAddress varchar(255),
        subject varchar(255),
        primary key (id)
    );

    create table task_comment (
        id int8 not null,
        addedAt timestamp,
        text text,
        addedBy_id varchar(255),
        TaskData_Comments_Id int8,
        primary key (id)
    );

    alter table Notification_email_header 
        add constraint UK_ptaka5kost68h7l3wflv7w6y8 unique (emailHeaders_id);

    alter table Attachment 
        add constraint FK_7ndpfa311i50bq7hy18q05va3 
        foreign key (attachedBy_id) 
        references OrganizationalEntity;

    alter table Attachment 
        add constraint FK_hqupx569krp0f0sgu9kh87513 
        foreign key (TaskData_Attachments_Id) 
        references Task;

    alter table BooleanExpression 
        add constraint FK_394nf2qoc0k9ok6omgd6jtpso 
        foreign key (Escalation_Constraints_Id) 
        references Escalation;

    alter table CorrelationPropertyInfo 
        add constraint FK_hrmx1m882cejwj9c04ixh50i4 
        foreign key (correlationKey_keyId) 
        references CorrelationKeyInfo;

    alter table Deadline 
        add constraint FK_68w742sge00vco2cq3jhbvmgx 
        foreign key (Deadlines_StartDeadLine_Id) 
        references Task;

    alter table Deadline 
        add constraint FK_euoohoelbqvv94d8a8rcg8s5n 
        foreign key (Deadlines_EndDeadLine_Id) 
        references Task;

    alter table Delegation_delegates 
        add constraint FK_gn7ula51sk55wj1o1m57guqxb 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table Delegation_delegates 
        add constraint FK_fajq6kossbsqwr3opkrctxei3 
        foreign key (task_id) 
        references Task;

    alter table Escalation 
        add constraint FK_ay2gd4fvl9yaapviyxudwuvfg 
        foreign key (Deadline_Escalation_Id) 
        references Deadline;

    alter table EventTypes 
        add constraint FK_nrecj4617iwxlc65ij6m7lsl1 
        foreign key (InstanceId) 
        references ProcessInstanceInfo;

    alter table I18NText 
        add constraint FK_k16jpgrh67ti9uedf6konsu1p 
        foreign key (Task_Subjects_Id) 
        references Task;

    alter table I18NText 
        add constraint FK_fd9uk6hemv2dx1ojovo7ms3vp 
        foreign key (Task_Names_Id) 
        references Task;

    alter table I18NText 
        add constraint FK_4eyfp69ucrron2hr7qx4np2fp 
        foreign key (Task_Descriptions_Id) 
        references Task;

    alter table I18NText 
        add constraint FK_pqarjvvnwfjpeyb87yd7m0bfi 
        foreign key (Reassignment_Documentation_Id) 
        references Reassignment;

    alter table I18NText 
        add constraint FK_o84rkh69r47ti8uv4eyj7bmo2 
        foreign key (Notification_Subjects_Id) 
        references Notification;

    alter table I18NText 
        add constraint FK_g1trxri8w64enudw2t1qahhk5 
        foreign key (Notification_Names_Id) 
        references Notification;

    alter table I18NText 
        add constraint FK_qoce92c70adem3ccb3i7lec8x 
        foreign key (Notification_Documentation_Id) 
        references Notification;

    alter table I18NText 
        add constraint FK_bw8vmpekejxt1ei2ge26gdsry 
        foreign key (Notification_Descriptions_Id) 
        references Notification;

    alter table I18NText 
        add constraint FK_21qvifarxsvuxeaw5sxwh473w 
        foreign key (Deadline_Documentation_Id) 
        references Deadline;

    alter table Notification 
        add constraint FK_bdbeml3768go5im41cgfpyso9 
        foreign key (Escalation_Notifications_Id) 
        references Escalation;

    alter table Notification_BAs 
        add constraint FK_mfbsnbrhth4rjhqc2ud338s4i 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table Notification_BAs 
        add constraint FK_fc0uuy76t2bvxaxqysoo8xts7 
        foreign key (task_id) 
        references Notification;

    alter table Notification_Recipients 
        add constraint FK_blf9jsrumtrthdaqnpwxt25eu 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table Notification_Recipients 
        add constraint FK_3l244pj8sh78vtn9imaymrg47 
        foreign key (task_id) 
        references Notification;

    alter table Notification_email_header 
        add constraint FK_ptaka5kost68h7l3wflv7w6y8 
        foreign key (emailHeaders_id) 
        references email_header;

    alter table Notification_email_header 
        add constraint FK_eth4nvxn21fk1vnju85vkjrai 
        foreign key (Notification_id) 
        references Notification;

    alter table PeopleAssignments_BAs 
        add constraint FK_t38xbkrq6cppifnxequhvjsl2 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table PeopleAssignments_BAs 
        add constraint FK_omjg5qh7uv8e9bolbaq7hv6oh 
        foreign key (task_id) 
        references Task;

    alter table PeopleAssignments_ExclOwners 
        add constraint FK_pth28a73rj6bxtlfc69kmqo0a 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table PeopleAssignments_ExclOwners 
        add constraint FK_b8owuxfrdng050ugpk0pdowa7 
        foreign key (task_id) 
        references Task;

    alter table PeopleAssignments_PotOwners 
        add constraint FK_tee3ftir7xs6eo3fdvi3xw026 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table PeopleAssignments_PotOwners 
        add constraint FK_4dv2oji7pr35ru0w45trix02x 
        foreign key (task_id) 
        references Task;

    alter table PeopleAssignments_Recipients 
        add constraint FK_4g7y3wx6gnokf6vycgpxs83d6 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table PeopleAssignments_Recipients 
        add constraint FK_enhk831fghf6akjilfn58okl4 
        foreign key (task_id) 
        references Task;

    alter table PeopleAssignments_Stakeholders 
        add constraint FK_met63inaep6cq4ofb3nnxi4tm 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table PeopleAssignments_Stakeholders 
        add constraint FK_4bh3ay74x6ql9usunubttfdf1 
        foreign key (task_id) 
        references Task;

    alter table Reassignment 
        add constraint FK_pnpeue9hs6kx2ep0sp16b6kfd 
        foreign key (Escalation_Reassignments_Id) 
        references Escalation;

    alter table Reassignment_potentialOwners 
        add constraint FK_8frl6la7tgparlnukhp8xmody 
        foreign key (entity_id) 
        references OrganizationalEntity;

    alter table Reassignment_potentialOwners 
        add constraint FK_qbega5ncu6b9yigwlw55aeijn 
        foreign key (task_id) 
        references Reassignment;

    alter table Task 
        add constraint FK_dpk0f9ucm14c78bsxthh7h8yh 
        foreign key (taskInitiator_id) 
        references OrganizationalEntity;

    alter table Task 
        add constraint FK_nh9nnt47f3l61qjlyedqt05rf 
        foreign key (actualOwner_id) 
        references OrganizationalEntity;

    alter table Task 
        add constraint FK_k02og0u71obf1uxgcdjx9rcjc 
        foreign key (createdBy_id) 
        references OrganizationalEntity;

    alter table task_comment 
        add constraint FK_aax378yjnsmw9kb9vsu994jjv 
        foreign key (addedBy_id) 
        references OrganizationalEntity;

    alter table task_comment 
        add constraint FK_1ws9jdmhtey6mxu7jb0r0ufvs 
        foreign key (TaskData_Comments_Id) 
        references Task;

    create sequence ATTACHMENT_ID_SEQ;

    create sequence BAM_TASK_ID_SEQ;

    create sequence BOOLEANEXPR_ID_SEQ;

    create sequence COMMENT_ID_SEQ;

    create sequence CONTENT_ID_SEQ;

    create sequence CONTEXT_MAPPING_INFO_ID_SEQ;

    create sequence CORRELATION_KEY_ID_SEQ;

    create sequence CORRELATION_PROP_ID_SEQ;

    create sequence DEADLINE_ID_SEQ;

    create sequence EMAILNOTIFHEAD_ID_SEQ;

    create sequence ESCALATION_ID_SEQ;

    create sequence I18NTEXT_ID_SEQ;

    create sequence NODE_INST_LOG_ID_SEQ;

    create sequence NOTIFICATION_ID_SEQ;

    create sequence PROCESS_INSTANCE_INFO_ID_SEQ;

    create sequence PROC_INST_EVENT_ID_SEQ;

    create sequence PROC_INST_LOG_ID_SEQ;

    create sequence REASSIGNMENT_ID_SEQ;

    create sequence SESSIONINFO_ID_SEQ;

    create sequence TASK_DEF_ID_SEQ;

    create sequence TASK_EVENT_ID_SEQ;

    create sequence TASK_ID_SEQ;

    create sequence VAR_INST_LOG_ID_SEQ;

    create sequence WORKITEMINFO_ID_SEQ;

    create sequence hibernate_sequence;

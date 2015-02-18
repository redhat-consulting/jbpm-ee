
    drop table Attachment cascade constraints;

    drop table BAMTaskSummary cascade constraints;

    drop table BooleanExpression cascade constraints;

    drop table Content cascade constraints;

    drop table ContextMappingInfo cascade constraints;

    drop table CorrelationKeyInfo cascade constraints;

    drop table CorrelationPropertyInfo cascade constraints;

    drop table Deadline cascade constraints;

    drop table Delegation_delegates cascade constraints;

    drop table Escalation cascade constraints;

    drop table EventTypes cascade constraints;

    drop table I18NText cascade constraints;

    drop table KieBaseXProcessInstance cascade constraints;

    drop table NodeInstanceLog cascade constraints;

    drop table Notification cascade constraints;

    drop table Notification_BAs cascade constraints;

    drop table Notification_Recipients cascade constraints;

    drop table Notification_email_header cascade constraints;

    drop table OrganizationalEntity cascade constraints;

    drop table PeopleAssignments_BAs cascade constraints;

    drop table PeopleAssignments_ExclOwners cascade constraints;

    drop table PeopleAssignments_PotOwners cascade constraints;

    drop table PeopleAssignments_Recipients cascade constraints;

    drop table PeopleAssignments_Stakeholders cascade constraints;

    drop table PresentationElement cascade constraints;

    drop table ProcessInstanceEventInfo cascade constraints;

    drop table ProcessInstanceInfo cascade constraints;

    drop table ProcessInstanceLog cascade constraints;

    drop table Reassignment cascade constraints;

    drop table Reassignment_potentialOwners cascade constraints;

    drop table SessionInfo cascade constraints;

    drop table Task cascade constraints;

    drop table TaskDef cascade constraints;

    drop table TaskEvent cascade constraints;

    drop table VariableInstanceLog cascade constraints;

    drop table WorkItemInfo cascade constraints;

    drop table email_header cascade constraints;

    drop table task_comment cascade constraints;

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
        id number(19,0) not null,
        accessType number(10,0),
        attachedAt timestamp,
        attachmentContentId number(19,0) not null,
        contentType varchar2(255 char),
        name varchar2(255 char),
        attachment_size number(10,0),
        attachedBy_id varchar2(255 char),
        TaskData_Attachments_Id number(19,0),
        primary key (id)
    );

    create table BAMTaskSummary (
        pk number(19,0) not null,
        createdDate timestamp,
        duration number(19,0),
        endDate timestamp,
        processInstanceId number(19,0) not null,
        startDate timestamp,
        status varchar2(255 char),
        taskId number(19,0) not null,
        taskName varchar2(255 char),
        userId varchar2(255 char),
        OPTLOCK number(10,0),
        primary key (pk)
    );

    create table BooleanExpression (
        id number(19,0) not null,
        expression clob,
        type varchar2(255 char),
        Escalation_Constraints_Id number(19,0),
        primary key (id)
    );

    create table Content (
        id number(19,0) not null,
        content blob,
        primary key (id)
    );

    create table ContextMappingInfo (
        mappingId number(19,0) not null,
        CONTEXT_ID varchar2(255 char) not null,
        KSESSION_ID number(10,0) not null,
        OPTLOCK number(10,0),
        primary key (mappingId)
    );

    create table CorrelationKeyInfo (
        keyId number(19,0) not null,
        name varchar2(255 char),
        processInstanceId number(19,0) not null,
        OPTLOCK number(10,0),
        primary key (keyId)
    );

    create table CorrelationPropertyInfo (
        propertyId number(19,0) not null,
        name varchar2(255 char),
        value varchar2(255 char),
        OPTLOCK number(10,0),
        correlationKey_keyId number(19,0),
        primary key (propertyId)
    );

    create table Deadline (
        id number(19,0) not null,
        deadline_date timestamp,
        escalated number(5,0),
        Deadlines_StartDeadLine_Id number(19,0),
        Deadlines_EndDeadLine_Id number(19,0),
        primary key (id)
    );

    create table Delegation_delegates (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table Escalation (
        id number(19,0) not null,
        name varchar2(255 char),
        Deadline_Escalation_Id number(19,0),
        primary key (id)
    );

    create table EventTypes (
        InstanceId number(19,0) not null,
        element varchar2(255 char)
    );

    create table I18NText (
        id number(19,0) not null,
        language varchar2(255 char),
        shortText varchar2(255 char),
        text clob,
        Task_Subjects_Id number(19,0),
        Task_Names_Id number(19,0),
        Task_Descriptions_Id number(19,0),
        Reassignment_Documentation_Id number(19,0),
        Notification_Subjects_Id number(19,0),
        Notification_Names_Id number(19,0),
        Notification_Documentation_Id number(19,0),
        Notification_Descriptions_Id number(19,0),
        Deadline_Documentation_Id number(19,0),
        primary key (id)
    );

    create table KieBaseXProcessInstance (
        kieProcessInstanceId number(19,0) not null,
        releaseArtifactId varchar2(255 char) not null,
        releaseGroupId varchar2(255 char) not null,
        releaseVersion varchar2(255 char) not null,
        OPTLOCK number(10,0),
        primary key (kieProcessInstanceId)
    );

    create table NodeInstanceLog (
        id number(19,0) not null,
        connection varchar2(255 char),
        log_date timestamp,
        externalId varchar2(255 char),
        nodeId varchar2(255 char),
        nodeInstanceId varchar2(255 char),
        nodeName varchar2(255 char),
        nodeType varchar2(255 char),
        processId varchar2(255 char),
        processInstanceId number(19,0) not null,
        type number(10,0) not null,
        workItemId number(19,0),
        primary key (id)
    );

    create table Notification (
        DTYPE varchar2(31 char) not null,
        id number(19,0) not null,
        priority number(10,0) not null,
        Escalation_Notifications_Id number(19,0),
        primary key (id)
    );

    create table Notification_BAs (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table Notification_Recipients (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table Notification_email_header (
        Notification_id number(19,0) not null,
        emailHeaders_id number(19,0) not null,
        mapkey varchar2(255 char) not null,
        primary key (Notification_id, mapkey)
    );

    create table OrganizationalEntity (
        DTYPE varchar2(31 char) not null,
        id varchar2(255 char) not null,
        primary key (id)
    );

    create table PeopleAssignments_BAs (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table PeopleAssignments_ExclOwners (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table PeopleAssignments_PotOwners (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table PeopleAssignments_Recipients (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table PeopleAssignments_Stakeholders (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table PresentationElement (
        id number(19,0) not null,
        primary key (id)
    );

    create table ProcessInstanceEventInfo (
        id number(19,0) not null,
        eventType varchar2(255 char),
        processInstanceId number(19,0) not null,
        OPTLOCK number(10,0),
        primary key (id)
    );

    create table ProcessInstanceInfo (
        InstanceId number(19,0) not null,
        lastModificationDate timestamp,
        lastReadDate timestamp,
        processId varchar2(255 char),
        processInstanceByteArray blob,
        startDate timestamp,
        state number(10,0) not null,
        OPTLOCK number(10,0),
        primary key (InstanceId)
    );

    create table ProcessInstanceLog (
        id number(19,0) not null,
        duration number(19,0),
        end_date timestamp,
        externalId varchar2(255 char),
        user_identity varchar2(255 char),
        outcome varchar2(255 char),
        parentProcessInstanceId number(19,0),
        processId varchar2(255 char),
        processInstanceId number(19,0) not null,
        processName varchar2(255 char),
        processVersion varchar2(255 char),
        start_date timestamp,
        status number(10,0),
        primary key (id)
    );

    create table Reassignment (
        id number(19,0) not null,
        Escalation_Reassignments_Id number(19,0),
        primary key (id)
    );

    create table Reassignment_potentialOwners (
        task_id number(19,0) not null,
        entity_id varchar2(255 char) not null
    );

    create table SessionInfo (
        id number(10,0) not null,
        lastModificationDate timestamp,
        rulesByteArray blob,
        startDate timestamp,
        OPTLOCK number(10,0),
        primary key (id)
    );

    create table Task (
        id number(19,0) not null,
        archived number(5,0),
        allowedToDelegate varchar2(255 char),
        formName varchar2(255 char),
        priority number(10,0) not null,
        subTaskStrategy varchar2(255 char),
        activationTime timestamp,
        createdOn timestamp,
        deploymentId varchar2(255 char),
        documentAccessType number(10,0),
        documentContentId number(19,0) not null,
        documentType varchar2(255 char),
        expirationTime timestamp,
        faultAccessType number(10,0),
        faultContentId number(19,0) not null,
        faultName varchar2(255 char),
        faultType varchar2(255 char),
        outputAccessType number(10,0),
        outputContentId number(19,0) not null,
        outputType varchar2(255 char),
        parentId number(19,0) not null,
        previousStatus number(10,0),
        processId varchar2(255 char),
        processInstanceId number(19,0) not null,
        processSessionId number(10,0) not null,
        skipable number(1,0) not null,
        status varchar2(255 char),
        workItemId number(19,0) not null,
        taskType varchar2(255 char),
        OPTLOCK number(10,0),
        taskInitiator_id varchar2(255 char),
        actualOwner_id varchar2(255 char),
        createdBy_id varchar2(255 char),
        primary key (id)
    );

    create table TaskDef (
        id number(19,0) not null,
        name varchar2(255 char),
        priority number(10,0) not null,
        primary key (id)
    );

    create table TaskEvent (
        id number(19,0) not null,
        logTime timestamp,
        taskId number(19,0),
        type varchar2(255 char),
        userId varchar2(255 char),
        OPTLOCK number(10,0),
        primary key (id)
    );

    create table VariableInstanceLog (
        id number(19,0) not null,
        log_date timestamp,
        externalId varchar2(255 char),
        oldValue varchar2(255 char),
        processId varchar2(255 char),
        processInstanceId number(19,0) not null,
        value varchar2(255 char),
        variableId varchar2(255 char),
        variableInstanceId varchar2(255 char),
        primary key (id)
    );

    create table WorkItemInfo (
        workItemId number(19,0) not null,
        creationDate timestamp,
        name varchar2(255 char),
        processInstanceId number(19,0) not null,
        state number(19,0) not null,
        OPTLOCK number(10,0),
        workItemByteArray blob,
        primary key (workItemId)
    );

    create table email_header (
        id number(19,0) not null,
        body clob,
        fromAddress varchar2(255 char),
        language varchar2(255 char),
        replyToAddress varchar2(255 char),
        subject varchar2(255 char),
        primary key (id)
    );

    create table task_comment (
        id number(19,0) not null,
        addedAt timestamp,
        text clob,
        addedBy_id varchar2(255 char),
        TaskData_Comments_Id number(19,0),
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

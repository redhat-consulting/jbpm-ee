
    alter table Attachment 
        drop 
        foreign key FK_7ndpfa311i50bq7hy18q05va3;

    alter table Attachment 
        drop 
        foreign key FK_hqupx569krp0f0sgu9kh87513;

    alter table BooleanExpression 
        drop 
        foreign key FK_394nf2qoc0k9ok6omgd6jtpso;

    alter table CorrelationPropertyInfo 
        drop 
        foreign key FK_hrmx1m882cejwj9c04ixh50i4;

    alter table Deadline 
        drop 
        foreign key FK_68w742sge00vco2cq3jhbvmgx;

    alter table Deadline 
        drop 
        foreign key FK_euoohoelbqvv94d8a8rcg8s5n;

    alter table Delegation_delegates 
        drop 
        foreign key FK_gn7ula51sk55wj1o1m57guqxb;

    alter table Delegation_delegates 
        drop 
        foreign key FK_fajq6kossbsqwr3opkrctxei3;

    alter table Escalation 
        drop 
        foreign key FK_ay2gd4fvl9yaapviyxudwuvfg;

    alter table EventTypes 
        drop 
        foreign key FK_nrecj4617iwxlc65ij6m7lsl1;

    alter table I18NText 
        drop 
        foreign key FK_k16jpgrh67ti9uedf6konsu1p;

    alter table I18NText 
        drop 
        foreign key FK_fd9uk6hemv2dx1ojovo7ms3vp;

    alter table I18NText 
        drop 
        foreign key FK_4eyfp69ucrron2hr7qx4np2fp;

    alter table I18NText 
        drop 
        foreign key FK_pqarjvvnwfjpeyb87yd7m0bfi;

    alter table I18NText 
        drop 
        foreign key FK_o84rkh69r47ti8uv4eyj7bmo2;

    alter table I18NText 
        drop 
        foreign key FK_g1trxri8w64enudw2t1qahhk5;

    alter table I18NText 
        drop 
        foreign key FK_qoce92c70adem3ccb3i7lec8x;

    alter table I18NText 
        drop 
        foreign key FK_bw8vmpekejxt1ei2ge26gdsry;

    alter table I18NText 
        drop 
        foreign key FK_21qvifarxsvuxeaw5sxwh473w;

    alter table Notification 
        drop 
        foreign key FK_bdbeml3768go5im41cgfpyso9;

    alter table Notification_BAs 
        drop 
        foreign key FK_mfbsnbrhth4rjhqc2ud338s4i;

    alter table Notification_BAs 
        drop 
        foreign key FK_fc0uuy76t2bvxaxqysoo8xts7;

    alter table Notification_Recipients 
        drop 
        foreign key FK_blf9jsrumtrthdaqnpwxt25eu;

    alter table Notification_Recipients 
        drop 
        foreign key FK_3l244pj8sh78vtn9imaymrg47;

    alter table Notification_email_header 
        drop 
        foreign key FK_ptaka5kost68h7l3wflv7w6y8;

    alter table Notification_email_header 
        drop 
        foreign key FK_eth4nvxn21fk1vnju85vkjrai;

    alter table PeopleAssignments_BAs 
        drop 
        foreign key FK_t38xbkrq6cppifnxequhvjsl2;

    alter table PeopleAssignments_BAs 
        drop 
        foreign key FK_omjg5qh7uv8e9bolbaq7hv6oh;

    alter table PeopleAssignments_ExclOwners 
        drop 
        foreign key FK_pth28a73rj6bxtlfc69kmqo0a;

    alter table PeopleAssignments_ExclOwners 
        drop 
        foreign key FK_b8owuxfrdng050ugpk0pdowa7;

    alter table PeopleAssignments_PotOwners 
        drop 
        foreign key FK_tee3ftir7xs6eo3fdvi3xw026;

    alter table PeopleAssignments_PotOwners 
        drop 
        foreign key FK_4dv2oji7pr35ru0w45trix02x;

    alter table PeopleAssignments_Recipients 
        drop 
        foreign key FK_4g7y3wx6gnokf6vycgpxs83d6;

    alter table PeopleAssignments_Recipients 
        drop 
        foreign key FK_enhk831fghf6akjilfn58okl4;

    alter table PeopleAssignments_Stakeholders 
        drop 
        foreign key FK_met63inaep6cq4ofb3nnxi4tm;

    alter table PeopleAssignments_Stakeholders 
        drop 
        foreign key FK_4bh3ay74x6ql9usunubttfdf1;

    alter table Reassignment 
        drop 
        foreign key FK_pnpeue9hs6kx2ep0sp16b6kfd;

    alter table Reassignment_potentialOwners 
        drop 
        foreign key FK_8frl6la7tgparlnukhp8xmody;

    alter table Reassignment_potentialOwners 
        drop 
        foreign key FK_qbega5ncu6b9yigwlw55aeijn;

    alter table Task 
        drop 
        foreign key FK_dpk0f9ucm14c78bsxthh7h8yh;

    alter table Task 
        drop 
        foreign key FK_nh9nnt47f3l61qjlyedqt05rf;

    alter table Task 
        drop 
        foreign key FK_k02og0u71obf1uxgcdjx9rcjc;

    alter table task_comment 
        drop 
        foreign key FK_aax378yjnsmw9kb9vsu994jjv;

    alter table task_comment 
        drop 
        foreign key FK_1ws9jdmhtey6mxu7jb0r0ufvs;

    drop table if exists Attachment;

    drop table if exists BAMTaskSummary;

    drop table if exists BooleanExpression;

    drop table if exists Content;

    drop table if exists ContextMappingInfo;

    drop table if exists CorrelationKeyInfo;

    drop table if exists CorrelationPropertyInfo;

    drop table if exists Deadline;

    drop table if exists Delegation_delegates;

    drop table if exists Escalation;

    drop table if exists EventTypes;

    drop table if exists I18NText;

    drop table if exists KieBaseXProcessInstance;

    drop table if exists NodeInstanceLog;

    drop table if exists Notification;

    drop table if exists Notification_BAs;

    drop table if exists Notification_Recipients;

    drop table if exists Notification_email_header;

    drop table if exists OrganizationalEntity;

    drop table if exists PeopleAssignments_BAs;

    drop table if exists PeopleAssignments_ExclOwners;

    drop table if exists PeopleAssignments_PotOwners;

    drop table if exists PeopleAssignments_Recipients;

    drop table if exists PeopleAssignments_Stakeholders;

    drop table if exists PresentationElement;

    drop table if exists ProcessInstanceEventInfo;

    drop table if exists ProcessInstanceInfo;

    drop table if exists ProcessInstanceLog;

    drop table if exists Reassignment;

    drop table if exists Reassignment_potentialOwners;

    drop table if exists SessionInfo;

    drop table if exists Task;

    drop table if exists TaskDef;

    drop table if exists TaskEvent;

    drop table if exists VariableInstanceLog;

    drop table if exists WorkItemInfo;

    drop table if exists email_header;

    drop table if exists task_comment;

    create table Attachment (
        id bigint not null auto_increment,
        accessType integer,
        attachedAt datetime,
        attachmentContentId bigint not null,
        contentType varchar(255),
        name varchar(255),
        attachment_size integer,
        attachedBy_id varchar(255),
        TaskData_Attachments_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table BAMTaskSummary (
        pk bigint not null auto_increment,
        createdDate datetime,
        duration bigint,
        endDate datetime,
        processInstanceId bigint not null,
        startDate datetime,
        status varchar(255),
        taskId bigint not null,
        taskName varchar(255),
        userId varchar(255),
        OPTLOCK integer,
        primary key (pk)
    ) ENGINE=InnoDB;

    create table BooleanExpression (
        id bigint not null auto_increment,
        expression longtext,
        type varchar(255),
        Escalation_Constraints_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table Content (
        id bigint not null auto_increment,
        content longblob,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ContextMappingInfo (
        mappingId bigint not null auto_increment,
        CONTEXT_ID varchar(255) not null,
        KSESSION_ID integer not null,
        OPTLOCK integer,
        primary key (mappingId)
    ) ENGINE=InnoDB;

    create table CorrelationKeyInfo (
        keyId bigint not null auto_increment,
        name varchar(255),
        processInstanceId bigint not null,
        OPTLOCK integer,
        primary key (keyId)
    ) ENGINE=InnoDB;

    create table CorrelationPropertyInfo (
        propertyId bigint not null auto_increment,
        name varchar(255),
        value varchar(255),
        OPTLOCK integer,
        correlationKey_keyId bigint,
        primary key (propertyId)
    ) ENGINE=InnoDB;

    create table Deadline (
        id bigint not null auto_increment,
        deadline_date datetime,
        escalated smallint,
        Deadlines_StartDeadLine_Id bigint,
        Deadlines_EndDeadLine_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table Delegation_delegates (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table Escalation (
        id bigint not null auto_increment,
        name varchar(255),
        Deadline_Escalation_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table EventTypes (
        InstanceId bigint not null,
        element varchar(255)
    ) ENGINE=InnoDB;

    create table I18NText (
        id bigint not null auto_increment,
        language varchar(255),
        shortText varchar(255),
        text longtext,
        Task_Subjects_Id bigint,
        Task_Names_Id bigint,
        Task_Descriptions_Id bigint,
        Reassignment_Documentation_Id bigint,
        Notification_Subjects_Id bigint,
        Notification_Names_Id bigint,
        Notification_Documentation_Id bigint,
        Notification_Descriptions_Id bigint,
        Deadline_Documentation_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table KieBaseXProcessInstance (
        kieProcessInstanceId bigint not null,
        releaseArtifactId varchar(255) not null,
        releaseGroupId varchar(255) not null,
        releaseVersion varchar(255) not null,
        OPTLOCK integer,
        primary key (kieProcessInstanceId)
    ) ENGINE=InnoDB;

    create table NodeInstanceLog (
        id bigint not null auto_increment,
        connection varchar(255),
        log_date datetime,
        externalId varchar(255),
        nodeId varchar(255),
        nodeInstanceId varchar(255),
        nodeName varchar(255),
        nodeType varchar(255),
        processId varchar(255),
        processInstanceId bigint not null,
        type integer not null,
        workItemId bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table Notification (
        DTYPE varchar(31) not null,
        id bigint not null auto_increment,
        priority integer not null,
        Escalation_Notifications_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table Notification_BAs (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table Notification_Recipients (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table Notification_email_header (
        Notification_id bigint not null,
        emailHeaders_id bigint not null,
        mapkey varchar(255) not null,
        primary key (Notification_id, mapkey)
    ) ENGINE=InnoDB;

    create table OrganizationalEntity (
        DTYPE varchar(31) not null,
        id varchar(255) not null,
        primary key (id)
    ) ENGINE=InnoDB;

    create table PeopleAssignments_BAs (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table PeopleAssignments_ExclOwners (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table PeopleAssignments_PotOwners (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table PeopleAssignments_Recipients (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table PeopleAssignments_Stakeholders (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table PresentationElement (
        id bigint not null auto_increment,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ProcessInstanceEventInfo (
        id bigint not null auto_increment,
        eventType varchar(255),
        processInstanceId bigint not null,
        OPTLOCK integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ProcessInstanceInfo (
        InstanceId bigint not null auto_increment,
        lastModificationDate datetime,
        lastReadDate datetime,
        processId varchar(255),
        processInstanceByteArray longblob,
        startDate datetime,
        state integer not null,
        OPTLOCK integer,
        primary key (InstanceId)
    ) ENGINE=InnoDB;

    create table ProcessInstanceLog (
        id bigint not null auto_increment,
        duration bigint,
        end_date datetime,
        externalId varchar(255),
        user_identity varchar(255),
        outcome varchar(255),
        parentProcessInstanceId bigint,
        processId varchar(255),
        processInstanceId bigint not null,
        processName varchar(255),
        processVersion varchar(255),
        start_date datetime,
        status integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table Reassignment (
        id bigint not null auto_increment,
        Escalation_Reassignments_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table Reassignment_potentialOwners (
        task_id bigint not null,
        entity_id varchar(255) not null
    ) ENGINE=InnoDB;

    create table SessionInfo (
        id integer not null auto_increment,
        lastModificationDate datetime,
        rulesByteArray longblob,
        startDate datetime,
        OPTLOCK integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table Task (
        id bigint not null auto_increment,
        archived smallint,
        allowedToDelegate varchar(255),
        formName varchar(255),
        priority integer not null,
        subTaskStrategy varchar(255),
        activationTime datetime,
        createdOn datetime,
        deploymentId varchar(255),
        documentAccessType integer,
        documentContentId bigint not null,
        documentType varchar(255),
        expirationTime datetime,
        faultAccessType integer,
        faultContentId bigint not null,
        faultName varchar(255),
        faultType varchar(255),
        outputAccessType integer,
        outputContentId bigint not null,
        outputType varchar(255),
        parentId bigint not null,
        previousStatus integer,
        processId varchar(255),
        processInstanceId bigint not null,
        processSessionId integer not null,
        skipable boolean not null,
        status varchar(255),
        workItemId bigint not null,
        taskType varchar(255),
        OPTLOCK integer,
        taskInitiator_id varchar(255),
        actualOwner_id varchar(255),
        createdBy_id varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table TaskDef (
        id bigint not null auto_increment,
        name varchar(255),
        priority integer not null,
        primary key (id)
    ) ENGINE=InnoDB;

    create table TaskEvent (
        id bigint not null auto_increment,
        logTime datetime,
        taskId bigint,
        type varchar(255),
        userId varchar(255),
        OPTLOCK integer,
        primary key (id)
    ) ENGINE=InnoDB;

    create table VariableInstanceLog (
        id bigint not null auto_increment,
        log_date datetime,
        externalId varchar(255),
        oldValue varchar(255),
        processId varchar(255),
        processInstanceId bigint not null,
        value varchar(255),
        variableId varchar(255),
        variableInstanceId varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table WorkItemInfo (
        workItemId bigint not null auto_increment,
        creationDate datetime,
        name varchar(255),
        processInstanceId bigint not null,
        state bigint not null,
        OPTLOCK integer,
        workItemByteArray longblob,
        primary key (workItemId)
    ) ENGINE=InnoDB;

    create table email_header (
        id bigint not null auto_increment,
        body longtext,
        fromAddress varchar(255),
        language varchar(255),
        replyToAddress varchar(255),
        subject varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table task_comment (
        id bigint not null auto_increment,
        addedAt datetime,
        text longtext,
        addedBy_id varchar(255),
        TaskData_Comments_Id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    alter table Notification_email_header 
        add constraint UK_ptaka5kost68h7l3wflv7w6y8 unique (emailHeaders_id);

    alter table Attachment 
        add index FK_7ndpfa311i50bq7hy18q05va3 (attachedBy_id), 
        add constraint FK_7ndpfa311i50bq7hy18q05va3 
        foreign key (attachedBy_id) 
        references OrganizationalEntity (id);

    alter table Attachment 
        add index FK_hqupx569krp0f0sgu9kh87513 (TaskData_Attachments_Id), 
        add constraint FK_hqupx569krp0f0sgu9kh87513 
        foreign key (TaskData_Attachments_Id) 
        references Task (id);

    alter table BooleanExpression 
        add index FK_394nf2qoc0k9ok6omgd6jtpso (Escalation_Constraints_Id), 
        add constraint FK_394nf2qoc0k9ok6omgd6jtpso 
        foreign key (Escalation_Constraints_Id) 
        references Escalation (id);

    alter table CorrelationPropertyInfo 
        add index FK_hrmx1m882cejwj9c04ixh50i4 (correlationKey_keyId), 
        add constraint FK_hrmx1m882cejwj9c04ixh50i4 
        foreign key (correlationKey_keyId) 
        references CorrelationKeyInfo (keyId);

    alter table Deadline 
        add index FK_68w742sge00vco2cq3jhbvmgx (Deadlines_StartDeadLine_Id), 
        add constraint FK_68w742sge00vco2cq3jhbvmgx 
        foreign key (Deadlines_StartDeadLine_Id) 
        references Task (id);

    alter table Deadline 
        add index FK_euoohoelbqvv94d8a8rcg8s5n (Deadlines_EndDeadLine_Id), 
        add constraint FK_euoohoelbqvv94d8a8rcg8s5n 
        foreign key (Deadlines_EndDeadLine_Id) 
        references Task (id);

    alter table Delegation_delegates 
        add index FK_gn7ula51sk55wj1o1m57guqxb (entity_id), 
        add constraint FK_gn7ula51sk55wj1o1m57guqxb 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table Delegation_delegates 
        add index FK_fajq6kossbsqwr3opkrctxei3 (task_id), 
        add constraint FK_fajq6kossbsqwr3opkrctxei3 
        foreign key (task_id) 
        references Task (id);

    alter table Escalation 
        add index FK_ay2gd4fvl9yaapviyxudwuvfg (Deadline_Escalation_Id), 
        add constraint FK_ay2gd4fvl9yaapviyxudwuvfg 
        foreign key (Deadline_Escalation_Id) 
        references Deadline (id);

    alter table EventTypes 
        add index FK_nrecj4617iwxlc65ij6m7lsl1 (InstanceId), 
        add constraint FK_nrecj4617iwxlc65ij6m7lsl1 
        foreign key (InstanceId) 
        references ProcessInstanceInfo (InstanceId);

    alter table I18NText 
        add index FK_k16jpgrh67ti9uedf6konsu1p (Task_Subjects_Id), 
        add constraint FK_k16jpgrh67ti9uedf6konsu1p 
        foreign key (Task_Subjects_Id) 
        references Task (id);

    alter table I18NText 
        add index FK_fd9uk6hemv2dx1ojovo7ms3vp (Task_Names_Id), 
        add constraint FK_fd9uk6hemv2dx1ojovo7ms3vp 
        foreign key (Task_Names_Id) 
        references Task (id);

    alter table I18NText 
        add index FK_4eyfp69ucrron2hr7qx4np2fp (Task_Descriptions_Id), 
        add constraint FK_4eyfp69ucrron2hr7qx4np2fp 
        foreign key (Task_Descriptions_Id) 
        references Task (id);

    alter table I18NText 
        add index FK_pqarjvvnwfjpeyb87yd7m0bfi (Reassignment_Documentation_Id), 
        add constraint FK_pqarjvvnwfjpeyb87yd7m0bfi 
        foreign key (Reassignment_Documentation_Id) 
        references Reassignment (id);

    alter table I18NText 
        add index FK_o84rkh69r47ti8uv4eyj7bmo2 (Notification_Subjects_Id), 
        add constraint FK_o84rkh69r47ti8uv4eyj7bmo2 
        foreign key (Notification_Subjects_Id) 
        references Notification (id);

    alter table I18NText 
        add index FK_g1trxri8w64enudw2t1qahhk5 (Notification_Names_Id), 
        add constraint FK_g1trxri8w64enudw2t1qahhk5 
        foreign key (Notification_Names_Id) 
        references Notification (id);

    alter table I18NText 
        add index FK_qoce92c70adem3ccb3i7lec8x (Notification_Documentation_Id), 
        add constraint FK_qoce92c70adem3ccb3i7lec8x 
        foreign key (Notification_Documentation_Id) 
        references Notification (id);

    alter table I18NText 
        add index FK_bw8vmpekejxt1ei2ge26gdsry (Notification_Descriptions_Id), 
        add constraint FK_bw8vmpekejxt1ei2ge26gdsry 
        foreign key (Notification_Descriptions_Id) 
        references Notification (id);

    alter table I18NText 
        add index FK_21qvifarxsvuxeaw5sxwh473w (Deadline_Documentation_Id), 
        add constraint FK_21qvifarxsvuxeaw5sxwh473w 
        foreign key (Deadline_Documentation_Id) 
        references Deadline (id);

    alter table Notification 
        add index FK_bdbeml3768go5im41cgfpyso9 (Escalation_Notifications_Id), 
        add constraint FK_bdbeml3768go5im41cgfpyso9 
        foreign key (Escalation_Notifications_Id) 
        references Escalation (id);

    alter table Notification_BAs 
        add index FK_mfbsnbrhth4rjhqc2ud338s4i (entity_id), 
        add constraint FK_mfbsnbrhth4rjhqc2ud338s4i 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table Notification_BAs 
        add index FK_fc0uuy76t2bvxaxqysoo8xts7 (task_id), 
        add constraint FK_fc0uuy76t2bvxaxqysoo8xts7 
        foreign key (task_id) 
        references Notification (id);

    alter table Notification_Recipients 
        add index FK_blf9jsrumtrthdaqnpwxt25eu (entity_id), 
        add constraint FK_blf9jsrumtrthdaqnpwxt25eu 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table Notification_Recipients 
        add index FK_3l244pj8sh78vtn9imaymrg47 (task_id), 
        add constraint FK_3l244pj8sh78vtn9imaymrg47 
        foreign key (task_id) 
        references Notification (id);

    alter table Notification_email_header 
        add index FK_ptaka5kost68h7l3wflv7w6y8 (emailHeaders_id), 
        add constraint FK_ptaka5kost68h7l3wflv7w6y8 
        foreign key (emailHeaders_id) 
        references email_header (id);

    alter table Notification_email_header 
        add index FK_eth4nvxn21fk1vnju85vkjrai (Notification_id), 
        add constraint FK_eth4nvxn21fk1vnju85vkjrai 
        foreign key (Notification_id) 
        references Notification (id);

    alter table PeopleAssignments_BAs 
        add index FK_t38xbkrq6cppifnxequhvjsl2 (entity_id), 
        add constraint FK_t38xbkrq6cppifnxequhvjsl2 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table PeopleAssignments_BAs 
        add index FK_omjg5qh7uv8e9bolbaq7hv6oh (task_id), 
        add constraint FK_omjg5qh7uv8e9bolbaq7hv6oh 
        foreign key (task_id) 
        references Task (id);

    alter table PeopleAssignments_ExclOwners 
        add index FK_pth28a73rj6bxtlfc69kmqo0a (entity_id), 
        add constraint FK_pth28a73rj6bxtlfc69kmqo0a 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table PeopleAssignments_ExclOwners 
        add index FK_b8owuxfrdng050ugpk0pdowa7 (task_id), 
        add constraint FK_b8owuxfrdng050ugpk0pdowa7 
        foreign key (task_id) 
        references Task (id);

    alter table PeopleAssignments_PotOwners 
        add index FK_tee3ftir7xs6eo3fdvi3xw026 (entity_id), 
        add constraint FK_tee3ftir7xs6eo3fdvi3xw026 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table PeopleAssignments_PotOwners 
        add index FK_4dv2oji7pr35ru0w45trix02x (task_id), 
        add constraint FK_4dv2oji7pr35ru0w45trix02x 
        foreign key (task_id) 
        references Task (id);

    alter table PeopleAssignments_Recipients 
        add index FK_4g7y3wx6gnokf6vycgpxs83d6 (entity_id), 
        add constraint FK_4g7y3wx6gnokf6vycgpxs83d6 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table PeopleAssignments_Recipients 
        add index FK_enhk831fghf6akjilfn58okl4 (task_id), 
        add constraint FK_enhk831fghf6akjilfn58okl4 
        foreign key (task_id) 
        references Task (id);

    alter table PeopleAssignments_Stakeholders 
        add index FK_met63inaep6cq4ofb3nnxi4tm (entity_id), 
        add constraint FK_met63inaep6cq4ofb3nnxi4tm 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table PeopleAssignments_Stakeholders 
        add index FK_4bh3ay74x6ql9usunubttfdf1 (task_id), 
        add constraint FK_4bh3ay74x6ql9usunubttfdf1 
        foreign key (task_id) 
        references Task (id);

    alter table Reassignment 
        add index FK_pnpeue9hs6kx2ep0sp16b6kfd (Escalation_Reassignments_Id), 
        add constraint FK_pnpeue9hs6kx2ep0sp16b6kfd 
        foreign key (Escalation_Reassignments_Id) 
        references Escalation (id);

    alter table Reassignment_potentialOwners 
        add index FK_8frl6la7tgparlnukhp8xmody (entity_id), 
        add constraint FK_8frl6la7tgparlnukhp8xmody 
        foreign key (entity_id) 
        references OrganizationalEntity (id);

    alter table Reassignment_potentialOwners 
        add index FK_qbega5ncu6b9yigwlw55aeijn (task_id), 
        add constraint FK_qbega5ncu6b9yigwlw55aeijn 
        foreign key (task_id) 
        references Reassignment (id);

    alter table Task 
        add index FK_dpk0f9ucm14c78bsxthh7h8yh (taskInitiator_id), 
        add constraint FK_dpk0f9ucm14c78bsxthh7h8yh 
        foreign key (taskInitiator_id) 
        references OrganizationalEntity (id);

    alter table Task 
        add index FK_nh9nnt47f3l61qjlyedqt05rf (actualOwner_id), 
        add constraint FK_nh9nnt47f3l61qjlyedqt05rf 
        foreign key (actualOwner_id) 
        references OrganizationalEntity (id);

    alter table Task 
        add index FK_k02og0u71obf1uxgcdjx9rcjc (createdBy_id), 
        add constraint FK_k02og0u71obf1uxgcdjx9rcjc 
        foreign key (createdBy_id) 
        references OrganizationalEntity (id);

    alter table task_comment 
        add index FK_aax378yjnsmw9kb9vsu994jjv (addedBy_id), 
        add constraint FK_aax378yjnsmw9kb9vsu994jjv 
        foreign key (addedBy_id) 
        references OrganizationalEntity (id);

    alter table task_comment 
        add index FK_1ws9jdmhtey6mxu7jb0r0ufvs (TaskData_Comments_Id), 
        add constraint FK_1ws9jdmhtey6mxu7jb0r0ufvs 
        foreign key (TaskData_Comments_Id) 
        references Task (id);

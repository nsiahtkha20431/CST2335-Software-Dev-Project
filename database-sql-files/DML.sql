SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
use `armada`;

insert into personnel(first_name,last_name,card_number,department_number,department_name,gender) values('john','doe',null,null,'','');
insert into personnel(first_name,last_name,card_number,department_number,department_name,gender) values('jane','doe',null,null,'','');
insert into personnel(first_name,last_name,card_number,department_number,department_name,gender) values('lorem','ipsum',null,null,'','');

insert into cards (issue_date,personnelID,ruleID) values(now(),1,null);
insert into cards (issue_date,personnelID,ruleID) values(now(),2,null);
insert into cards (issue_date,personnelID,ruleID) values(now(),3,null);


insert into account(account_name,account_password) values('admin','admin');

insert into devices values('Cam1',12346,'TCP',inet_aton('192.168.1.1'),00001);
insert into devices values('Cam2',123467,'TCP',inet_aton('192.168.2.1'),00002);

insert into rules(rule_name,rule_desc) values('Main Gate', 'This rule for ther person that only allowed access to main gate');




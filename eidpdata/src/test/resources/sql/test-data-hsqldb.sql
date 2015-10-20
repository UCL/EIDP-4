create user EIDP password 'EIDP';
create schema UCLBRIT authorization EIDP;
create table UCLBRIT.T_USERS (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 10, INCREMENT BY 1), login VARCHAR(50) NOT NULL, password VARCHAR(100) NOT NULL, login_err_number INTEGER, login_err_timestamp VARCHAR(80) NOT NULL, create_timestamp VARCHAR(80), modify_timestamp VARCHAR(80), center_id INTEGER NOT NULL, forename VARCHAR(100), surname VARCHAR(100));
insert into UCLBRIT.T_USERS (login, password, login_err_number, login_err_timestamp, create_timestamp, modify_timestamp, center_id) values ('testuser', 'password', 1, '1234567890', '0987654321', '0123456789', 1000);
grant all on UCLBRIT.T_USERS to EIDP;


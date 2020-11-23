# sas

School Administration System

-- RUN ---

Before you start running this application, you most have a MySQL server and "qldtdb" database.
If you are using different user/password then the default settings in the MySQL server, you have to change the

<b>spring.datasource.username</b>=<i>root</i></br>
<b>spring.datasource.password</b>=<i>yourpassword</i>

in the backend > src > main > resources > application.properties configuration file.

---

If you don't want init data you can set in
qldt/backend/src/main/java/edu/hanu/qldt/InitData.java by turning into a comment the the testData() method
in the line 55.

---

commands to run:

<b>Back-end: cd backend && mvn spring-boot:run</b> </br>
<b>Front-end: cd frontend && ng serve --open</b>

---

Default admin user login: admin/admin</br>
Default student user login: student[0-20]/student</br>
Default teacher user login: teacher[0-8]/teacher

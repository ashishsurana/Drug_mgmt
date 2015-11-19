# Drug_mgmt

The following things are required to execute the code successfully
  * Database is mysql
  * user is "admin"
  * password is "password"
  * running on default 3306 port
  * database name is "stock"

The Database "stock" contains two tables "purchase" and "sales" 
PURCHASE
+----------+-------------+------+-----+---------+-------+
| Field    | Type        | Null | Key | Default | Extra |
+----------+-------------+------+-----+---------+-------+
| name     | varchar(30) | NO   |     | NULL    |       |
| bno      | varchar(10) | NO   |     | NULL    |       |
| qty      | int(11)     | NO   |     | NULL    |       |
| party    | varchar(30) | NO   |     | NULL    |       |
| billdate | date        | NO   |     | NULL    |       |
| billno   | int(11)     | NO   |     | NULL    |       |
| expdate  | date        | NO   |     | NULL    |       |
+----------+-------------+------+-----+---------+-------+

SALES
+--------+-------------+------+-----+---------+-------+
| Field  | Type        | Null | Key | Default | Extra |
+--------+-------------+------+-----+---------+-------+
| name   | varchar(30) | NO   |     | NULL    |       |
| bno    | varchar(10) | NO   |     | NULL    |       |
| qty    | int(11)     | NO   |     | NULL    |       |
| billno | int(11)     | NO   |     | NULL    |       |
| drname | varchar(15) | NO   |     | NULL    |       |
| pname  | varchar(20) | NO   |     | NULL    |       |
| date   | varchar(10) | NO   |     | NULL    |       |
+--------+-------------+------+-----+---------+-------+

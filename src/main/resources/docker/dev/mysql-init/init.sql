CREATE USER '${spring.datasource.username}'@'%' IDENTIFIED BY '${spring.datasource.password}';
GRANT SELECT, INSERT,UPDATE, DELETE ON ${spring.datasource.name}.* TO '${spring.datasource.username}'@'%';
GRANT ALTER, CREATE, DROP ON ${spring.datasource.name}.* TO '${spring.datasource.username}'@'%';
FLUSH PRIVILEGES;

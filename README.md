# Login module

Simple register and login module for any java program. Uses a MySQL Database which connects by the JDBC Driver [(Maven Dependency)](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

## How to integrate in a program

The action after sucessful login is declarated by a

```java
while (sucefulLogin) {
    //HERE TODO AFTER SUCEFUL LOGIN
}
```

## About the Database

The default database is a private database hosted by https://www.freemysqlhosting.net/
You can implement your own with the use of the next methods:

 Method | Use | Parameters
 --- | --- | ---
 .setUrl | Set the database url | String that starts with jdbc:mysql://
 .setUsername | Set database username | String
 .setPassword | Set database password | String

# Contributing

Bug reports and commit requests are welcome.

# Examples

[Text Editor](https://github.com/JorgeDress/textEditor) is a program that uses this plugin for integration with the cloud.

# License

[GNU General Public License 3.0](https://choosealicense.com/licenses/gpl-3.0/#)

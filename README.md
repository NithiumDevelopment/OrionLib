# OrionLib
Java MySQL library.

## Install dependency
Gradle:
```gradle
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
```gradle
	dependencies {
	        implementation 'com.github.NithiumDevelopment:OrionLib:Tag'
	}
```
Maven:
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
```xml
	<dependency>
	    <groupId>com.github.NithiumDevelopment</groupId>
	    <artifactId>OrionLib</artifactId>
	    <version>vwrsion</version>
	</dependency>
```

## Usage
### QueryGet Usage
QueryGet is used for get object from Database.
Example User.

User class:
```java
package id.nithium.libraries.test.orionlib.object;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @NonNull
    private UUID uuid;

    @NonNull
    private String username;
}
```
Main class:
```java
package id.nithium.libraries.test.orionlib;

import id.nithium.libraries.orionlib.OrionLib;
import id.nithium.libraries.orionlib.query.QueryGet;
import id.nithium.libraries.test.orionlib.object.User;
import lombok.Setter;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Main {

    @Getter
    private static OrionLib orionLib;
    @Getter
    private static Connection connection;

    public static void main(String[] args) {
        QueryGet<User> userQueryGet = new QueryGet<>(connection), "select id from database where username = ?", resultSet -> {
            User user = new User(uuid), "username123");

            while (resultSet.next()) {
                user.setUuid(UUID.fromString(resultSet.getString("id")));
            }

            return user;
        }, List.of("username123"));

        assert userQueryGet.isPresent();
        User user = userQueryGet.get();
        System.out.println("User: " + user.getUuid() + ", "  + user.getUsername());
    }
}

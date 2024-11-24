# OrionLib

OrionLib is a MySQL library built with the Java programming language. It makes it easier for developers to execute MySQL commands, especially for operations like ``SELECT`` and ``UPDATE``. It also uses dependencies such as HikariCP and mysql-connector-java.

## Getting started

### Add OrionLib as dependency
[![](https://jitpack.io/v/NithiumDevelopment/OrionLib.svg)](https://jitpack.io/#NithiumDevelopment/OrionLib)

To use this project recommeded to use latest commit, or available commit with status **OK**

<details>
    <summary>Maven</summary>

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.NithiumDevelopment</groupId>
        <artifactId>OrionLib</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```
</details>

<details>
	<summary>Gradle</summary>

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.ForestTechMC:ForestRedisAPI:VERSION'
}
```
</details>

## Example

Example of how to use OrionLib.

### User example

Here is an example of an User class for QueryGet, ObjectUpdater.

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

### QueryGet usagement

To get object from ``SELECT`` you need few 3 object: Connection, Query, List Class

```java
// Username.
String username = "username123";
QueryGet<User> userQueryGet = new QueryGet<>(connection, "SELECT id FROM table WHERE username = ?", resultSet -> { // use ResultSet to do something, e.g: initialize the class.
    // Initialize User class or you could do something
    User user = new User(uuid, username);

    // call resultSet.next()
    while (resultSet.next()) {
        user.setUuid(UUID.fromString(resultSet.getString("id")));
    }

    return user;
}, /*Use this as required object when executing WHERE. If empty just type List.of()*/ List.of(username));

// You can check if the is present in QueryGet
if (!userQueryGet.isPresent()) {
    // Do something
}

// Get the object
User user = userQueryGet.get();

System.out.println("User: " + user.getUuid() + ", "  + user.getUsername());
```

### QuerySet usagement

To set existing column or execute anything else.

```java
// Usagement 1
new QuerySet(connection, "UPDATE table SET id = ? WHERE username = ?", List.of(uuid, username));

// Usagement 2
QuerySet querySet = new QuerySet(connection, "UPDATE table SET id = ? WHERE username = ?", List.of(uuid, username));
// You can check if thr query is executed.
if (querySet.next()) {
    // Do something
}

// Usagement 3
new QuerySet(connection, "CREATE TABLE IF NOT EXISTS table(a VARCHAR(255), PRIMARY KEY (a))", List.of());

// Usagement..
```

### ObjectUpdater usagement

To set current variable class to database.

> [!NOTE]
> ObjectUpdater still on development (Also unoptimized)

```java
// ..

ObjectUpdater objectUpdater = new ObjectUpdater(connection, "table-name", /*User class that has different variable from database*/ user);
objectUpdater.setup();
```

After doing that:

Before:

| uuid | username |
| --- | --- |
| fe8b98aa-9c18-415c-a2d4-8aba642088ce | username123 |
| .. | .. |

After:

| uuid | username |
| --- | --- |
| fe8b98aa-9c18-415c-a2d4-8aba642088ce | username456 | 
| .. | .. |

## License

OrionLib is licensed under the permissive of MIT LICENSE, for more information please read [`LICENSE`](https://github.com/NithiumDevelopment/OrionLib/blob/master/LICENSE)

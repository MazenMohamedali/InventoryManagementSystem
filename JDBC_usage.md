Here’s a concise cheat sheet for using **JDBC with SQLite**:  

---

### **Setup**
- **Driver:** `org.sqlite.JDBC`
- **Connection URL Format:**  
  ```
  jdbc:sqlite:<path_to_db_file>
  ```
  Example: `jdbc:sqlite:mydatabase.db`  
  Use `:memory:` for an in-memory database.

---

### **Basic Workflow**
1. **Load Driver**: (Optional for modern JDBC)  
   ```java
   Class.forName("org.sqlite.JDBC");
   ```

2. **Connect to Database**:  
   ```java
   Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
   ```

3. **Create Statement**:  
   ```java
   Statement stmt = conn.createStatement();
   ```

4. **Execute Queries**:
   - **DDL (Create/Alter/Drop)**:  
     ```java
     stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, email TEXT);");
     ```
   - **DML (Insert/Update/Delete)**:  
     ```java
     stmt.executeUpdate("INSERT INTO users (name, email) VALUES ('Alice', 'alice@example.com');");
     ```

5. **Retrieve Data**:  
   ```java
   ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
   while (rs.next()) {
       System.out.println(rs.getInt("id") + ", " + rs.getString("name") + ", " + rs.getString("email"));
   }
   ```

6. **Close Resources**:  
   ```java
   rs.close();
   stmt.close();
   conn.close();
   ```

---

### **Prepared Statements**
For dynamic queries:  
```java
String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, "Bob");
pstmt.setString(2, "bob@example.com");
pstmt.executeUpdate();
pstmt.close();
```

---

### **Common Connection URL Variants**
- **File-based DB:**  
  `jdbc:sqlite:sample.db`
- **In-memory DB:**  
  `jdbc:sqlite::memory:`

---

### **SQL Command Examples**
| Operation              | Example SQL                             |
|------------------------|-----------------------------------------|
| **Create Table**        | `CREATE TABLE users (id INTEGER, name TEXT);` |
| **Insert Data**         | `INSERT INTO users (name) VALUES ('Alice');` |
| **Update Data**         | `UPDATE users SET name = 'Bob' WHERE id = 1;` |
| **Delete Data**         | `DELETE FROM users WHERE id = 1;`       |
| **Select Data**         | `SELECT * FROM users;`                 |
| **Drop Table**          | `DROP TABLE users;`                   |

---

### **Error Handling**
Wrap operations in a `try-catch` block:  
```java
try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
    // Your database code here
} catch (SQLException e) {
    System.out.println("Error: " + e.getMessage());
}
```

---

Use this cheat sheet to quickly build and manage your SQLite database interactions with JDBC. Let me know if you need further examples!

Here are the **most important JDBC functions** for SQLite (or any database) and how to use them:

---

### **1. Establish Connection**
**Purpose**: Connect to the database.

```java
Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
```
- `sample.db`: SQLite database file. SQLite will create the file if it doesn't exist.
- **Close connection** when done:
  ```java
  conn.close();
  ```

---

### **2. Execute SQL Statements**
**Purpose**: Run SQL commands (DDL/DML).  

#### **Statement**
For simple queries:
```java
Statement stmt = conn.createStatement();

// Create table
stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, email TEXT);");

// Insert data
stmt.executeUpdate("INSERT INTO users (name, email) VALUES ('Alice', 'alice@example.com');");

// Close statement
stmt.close();
```

---

### **3. Use Prepared Statements**
**Purpose**: Safer, prevents SQL injection, supports dynamic parameters.

```java
String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
PreparedStatement pstmt = conn.prepareStatement(sql);

// Set parameters
pstmt.setString(1, "Bob");
pstmt.setString(2, "bob@example.com");

// Execute
pstmt.executeUpdate();

// Close prepared statement
pstmt.close();
```

---

### **4. Query Data**
**Purpose**: Retrieve and process results from a database.

```java
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM users");

// Process results
while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    String email = rs.getString("email");

    System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
}

// Close resources
rs.close();
stmt.close();
```

---

### **5. Transaction Control**
**Purpose**: Group multiple SQL statements into a transaction.

```java
try {
    conn.setAutoCommit(false); // Start transaction

    Statement stmt = conn.createStatement();
    stmt.executeUpdate("INSERT INTO users (name, email) VALUES ('Eve', 'eve@example.com');");
    stmt.executeUpdate("UPDATE users SET name = 'Eve Updated' WHERE id = 1;");

    conn.commit(); // Commit transaction
    stmt.close();
} catch (SQLException e) {
    conn.rollback(); // Rollback if error occurs
    e.printStackTrace();
} finally {
    conn.setAutoCommit(true); // Reset to default
}
```

---

### **6. Handle Exceptions**
**Purpose**: Catch and handle database errors.

```java
try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
    // Database operations
} catch (SQLException e) {
    System.out.println("Database error: " + e.getMessage());
}
```

---

### **Key Functions**
| **Function**                     | **Description**                                    | **Example**                                     |
|----------------------------------|--------------------------------------------------|-----------------------------------------------|
| `DriverManager.getConnection()`   | Connect to the database                          | `DriverManager.getConnection("jdbc:sqlite:db.db");` |
| `Statement.execute()`             | Execute SQL commands (DDL/DML)                  | `stmt.execute("CREATE TABLE...");`            |
| `Statement.executeUpdate()`       | Execute SQL commands (DML only)                 | `stmt.executeUpdate("INSERT INTO...");`       |
| `Statement.executeQuery()`        | Execute a SELECT query                          | `stmt.executeQuery("SELECT...");`             |
| `ResultSet.get<Type>()`           | Retrieve data from query results                | `rs.getInt("id"); rs.getString("name");`      |
| `PreparedStatement.set<Type>()`   | Set parameters in a prepared statement          | `pstmt.setString(1, "Alice");`                |
| `Connection.commit()`             | Commit a transaction                            | `conn.commit();`                              |
| `Connection.rollback()`           | Rollback a transaction                          | `conn.rollback();`                            |
| `Connection.close()`              | Close database connection                       | `conn.close();`                               |

---

This list covers all critical operations you’ll need for most SQLite tasks!
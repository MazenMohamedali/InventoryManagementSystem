Here's a **cheat sheet for logging** in Java:

---

### **1. Import the Logging Library**
Java provides the `java.util.logging` package for logging.

```java
import java.util.logging.*;
```

---

### **2. Create a Logger Instance**
Use the `Logger` class to create or get a logger.

```java
private static final Logger LOGGER = Logger.getLogger(MyClass.class.getName());
```

---

### **3. Set Logging Levels**
Levels define the severity of the log messages:
- `SEVERE`: Critical errors.
- `WARNING`: Warnings about potential issues.
- `INFO`: General informational messages.
- `CONFIG`: Configuration details.
- `FINE`, `FINER`, `FINEST`: Debugging messages.

Example:
```java
LOGGER.setLevel(Level.INFO);
```

---

### **4. Log Messages**
Use the appropriate level to log messages.

```java
LOGGER.severe("This is a SEVERE message");
LOGGER.warning("This is a WARNING message");
LOGGER.info("This is an INFO message");
LOGGER.config("This is a CONFIG message");
LOGGER.fine("This is a FINE message");
```

---

### **5. Add a Handler**
Handlers specify where the logs are output:
- **ConsoleHandler**: Logs to the console.
- **FileHandler**: Logs to a file.

#### Add a ConsoleHandler
```java
ConsoleHandler consoleHandler = new ConsoleHandler();
consoleHandler.setLevel(Level.ALL); // Set the handler level
LOGGER.addHandler(consoleHandler);
```

#### Add a FileHandler
```java
FileHandler fileHandler = new FileHandler("app.log", true); // true for appending
fileHandler.setLevel(Level.ALL); // Log all levels
LOGGER.addHandler(fileHandler);
```

---

### **6. Set a Formatter**
Formatters control the appearance of log messages.

#### SimpleFormatter
Default formatter for plain text logs.
```java
fileHandler.setFormatter(new SimpleFormatter());
```

#### Custom Formatter
You can create your own formatter.
```java
class CustomFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return "[" + record.getLevel() + "] " +
               record.getSourceClassName() + "." +
               record.getSourceMethodName() + ": " +
               record.getMessage() + "\n";
    }
}

fileHandler.setFormatter(new CustomFormatter());
```

---

### **7. Exception Logging**
Include stack traces with `Throwable`.

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    LOGGER.log(Level.SEVERE, "An exception occurred", e);
}
```

---

### **8. Configure Logging with Properties File**
You can use a `logging.properties` file for configuration.

#### Example `logging.properties`:
```properties
handlers= java.util.logging.ConsoleHandler
java.util.logging.ConsoleHandler.level = ALL
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
.level = INFO
```

#### Load Properties File:
```java
LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
```

---

### **9. Common Best Practices**
1. **Do not log sensitive information**: Avoid logging passwords, API keys, etc.
2. **Use appropriate levels**: Choose the correct level for each log message.
3. **Keep logs concise**: Avoid excessive or redundant logs.
4. **Rotate log files**: Use `FileHandler` with size limits to prevent large logs.
   ```java
   FileHandler rotatingFileHandler = new FileHandler("app.log", 1024 * 1024, 5, true); // 1MB, 5 files
   ```

---

Now you have a cheat sheet to implement logging effectively! Let me know if you want examples tailored for specific use cases. 🚀
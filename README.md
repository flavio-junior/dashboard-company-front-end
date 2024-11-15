# Documentation of Project | dashboard-company

- **Specifications:**
  - **Programming language used: ` Kotlin `**
  - **Kotlin Version: ` 1.9.25 `**
  - **JDK: ` 21 LTS `**
  - **Maven: ` 3.9.7 `**

- **Tools:**
  - **` IntelliJ IDEA Community Edition `**
  - **` Postman `**
  - **` Git `**
  - **` GitHub `**
  - **` pgAdmin 4 `**
  - **` CMD `**

- **Contact Me:**
  - **Name: ` Flávio Júnior `**
  - **Description: ` Main Developer `**
  - **Contact: ` flaviojunior.work@gmail.com `**

### Git - Accepted Standards for Commits and Pull Requests:
- **feat:** **` Used when creating or adding a new feature. `**
- **fix:** **`  Used when fixing a bug in the project. `**
- **update:** **`  Used for updates to existing files. `**
- **config:** **` Implementation and configuration in the project. `**
- **drop files:** **` Used when a file is deleted from the project. `**
- **docs:** **` Indicates the creation, implementation, or changes in the documentation. `**
- **test:** **` Used when adding or updating test scenarios. `**
- **refactor:** **` Refactoring that does not change functionality. `**
- **build:** **` Indicates the creation, implementation, or changes in build files or dependencies. `**
- **chore:** **` Indicates updates to build tasks, admin configurations, packages, etc. `**
- **perf:** **` Code changes related to performance. `**
- **style:** **`  Used for changes in code formatting. `**
- **revert:** **` Reverts to a specific commit. `**

**Example:** **` git commit -m "docs: adding instructions" `**

### Tree of Branchs:
- **Main: ` Default `**
- **Developer: ` Your Branch `**

# Commands
### Commands to generete file .jar:
- **Generate jar: ` mvn clean package `**
- **Generate jar and skip tests: ` mvn clean package -DskipTests `**
- **Run project: ` java -jar target/pontoguia-0.0.1-SNAPSHOT.jar.jar `**
- **Stop project: ` Ctrl + C `**

### Commands of flyway:
- **Migrate: ` mvn flyway:migrate `**
- **Clean: ` mvn flyway:clean (Execute this command only in development or testing environments). `**
- **Info: ` mvn flyway:info `**
- **Validade: ` mvn flyway:validate `**
- **Undo: ` mvn flyway:undo `**
- **Baseline: ` mvn flyway:baseline `**
- **Repair: ` mvn flyway:repair `**

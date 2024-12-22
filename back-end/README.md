# Documentation of Project | Back End

- **Environments:**
  - **Test**
  - **Dev**
  - **Prod**

# Commands
### Commands of Docker:
- **Create and run service with definitions of docker-compose,yml: ` docker compose up -d --build `**
- **Change environment:**
  - **Test: ` docker-compose --env-file .env.test up --build `**
  - **Dev: ` docker-compose --env-file .env.dev up --build `**
  - **Prod: ` docker-compose --env-file .env.prod up --build -d `** 

### Commands to generete file .jar:
- **Generate jar: ` mvn clean package `**
- **Generate jar and skip tests: ` mvn clean package -DskipTests `**
- **Run project: ` java -jar target/dashboard-company-0.0.1-SNAPSHOT.jar.jar `**
- **Stop project: ` Ctrl + C `**

### Commands of flyway:
- **Migrate: ` mvn flyway:migrate `**
- **Clean: ` mvn flyway:clean (Execute this command only in development or testing environments). `**
- **Info: ` mvn flyway:info `**
- **Validade: ` mvn flyway:validate `**

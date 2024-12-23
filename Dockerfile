# Use the Maven image to compile the project
FROM maven:3.8.1-openjdk-17-slim AS builder

# Add metadata labels
LABEL maintainer="<Your Name & Email>" \
      version="<version number>" \
      description="<Short Description>"

# Set the working directory
WORKDIR /automation

# Optimize layer caching by copying only pom.xml first
COPY pom.xml .

# Use specific maven goals and options for better performance
RUN mvn dependency:go-offline -B --no-transfer-progress

# Copy necessary project files
COPY src ./src
COPY test-suites ./test-suites
COPY testng.xml ./testng.xml

# Package the application
RUN mvn clean package -DskipTests -B --no-transfer-progress && rm -rf ~/.m2/repository

# Use an openjdk image from bellsoft
FROM bellsoft/liberica-openjdk-alpine:17.0.7-7

# Add metadata labels
LABEL maintainer="<Your Name & Email>" \
      version="<version number>" \
      description="<Short Description>"

# Create non-root user for security
RUN addgroup -S automation && adduser -S automation -G automation

# Set the working directory
WORKDIR /automation

# Create logs and test-output directories and set permissions
RUN mkdir -p /automation/logs /automation/test-output && chown -R automation:automation /automation/logs /automation/test-output

# Copy only required files from builder stage
COPY --from=builder --chown=automation:automation /automation/target/tafs/libs/ ./libs/
COPY --from=builder --chown=automation:automation /automation/test-suites/ ./test-suites/
COPY --from=builder --chown=automation:automation /automation/testng.xml ./testng.xml

# Define environment variables with defaults
ENV ENVIRONMENT=REMOTE \
    BROWSER=CHROME \
    GRID_URL=http://<selenium-grid-host-or-ip>:<port> \
    RECORD_VIDEO=false \
    HEADLESS=false \
    TEST_SUITE=Master.xml

# Set the entry point
ENTRYPOINT ["sh", "-c", "java -cp 'libs/*' -Denv=${ENVIRONMENT} -Dbrowser=${BROWSER} -DremoteSeleniumGridUrl=${GRID_URL} -DrecordVideo=${RECORD_VIDEO} -Dheadless=${HEADLESS} org.testng.TestNG test-suites/${TEST_SUITE}"]
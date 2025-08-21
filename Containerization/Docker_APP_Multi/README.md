
# JBoss EAP 7.1 Docker Image (Multi-Stage Build)

This project provides a **multi-stage Docker image** for deploying a Java EE application on **JBoss EAP 7.1**, with pre-configured dependencies and optimized image layers.

---

## Project Structure

```
.
├── Dockerfile
├── jboss-eap-7.1.7z
├── STRUTS2_MENU-XML.7z
```

- `Dockerfile`: Multi-stage build to set up JBoss and Java 8.
- `jboss-eap-7.1.7z`: Archived JBoss EAP 7.1 binaries.
- `STRUTS2_MENU-XML.7z`: Custom application or XML configurations.

---

## Features

- Multi-stage Docker build for clean and optimized final image
- Java 8 JDK installed using APT
- JBoss EAP 7.1 extracted and ready to run
- Final image exposes ports `8009` and `8080`
- Minimal Ubuntu base used for better compatibility and security

---

## Getting Started

### Prerequisites

- Docker installed on your system → [Install Docker](https://docs.docker.com/get-docker/)

### Build the Image

```bash
docker build -t jboss-app-image .
```

### Run the Container

```bash
docker run -d -p 8080:8080 -p 8009:8009 --name jboss-app jboss-app-image
```

### Access the Application

Once the container is running, open:

```
http://localhost:8080/
```

---

## Notes

- Ensure the archive files (`jboss-eap-7.1.7z` and `STRUTS2_MENU-XML.7z`) are valid and placed in the root directory before building the image.
- Make sure `runLOS.sh` inside JBoss’s `bin/` directory is configured and executable.

---

## License

This Dockerfile setup is provided for internal or educational use only.

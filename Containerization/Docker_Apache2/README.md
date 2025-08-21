# Apache HTTP Server Docker Image (Multi-Stage Build)
This project provides a lightweight, production-ready Docker image for the Apache HTTP Server using a multi-stage build strategy to optimize image size and reduce unnecessary bloat.

---

## Project Structure
```
.
├── Dockerfile
├── apache2.conf
├── 000-default.conf
```
- `Dockerfile`: Multi-stage Dockerfile for building a slim Apache image.
- `apache2.conf`: Custom main configuration file for Apache.
- `000-default.conf`: Virtual host configuration placed under `sites-enabled`.

---

## Features

- Multi-stage Docker build for cleaner final image
- Preconfigured Apache with custom configs
- Minimal Ubuntu base for enhanced security and portability
- Log directory exposed as a volume
- Image cleanup steps to reduce layers and size

---

## Getting Started

### Prerequisites

Ensure you have [Docker](https://docs.docker.com/get-docker/) installed on your system.

### Build the Image

```bash
docker build -t apache-server-image .
```

### Run the Container

```bash
docker run -d -p 80:80 -v $(pwd)/logs:/var/log/apache2 --name apache-server apache-server-image
```

> Logs will be stored in the `logs/` folder on the host machine.

---

## Configuration

The following configuration files are copied during build:

- `apache2.conf` → `/etc/apache2/apache2.conf`

Feel free to modify them to suit your environment before building the image.

---

## Access Logs

You can inspect the logs using:

```bash
tail -f ./logs/access.log
```

Or for error logs:

```bash
tail -f ./logs/error.log
```

---
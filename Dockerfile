FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY src ./src
COPY resources ./resources
RUN mkdir -p build/classes
RUN find src -name "*.java" > sources.txt
RUN javac -d build/classes @sources.txt
RUN cp -r resources/* build/classes/

FROM ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    xvfb \
    x11vnc \
    novnc \
    websockify \
    openbox \
    openjdk-17-jre \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=builder /app/build/classes ./build/classes
COPY start.sh .
RUN chmod +x start.sh

# Render uses the PORT environment variable (default 10000)
ENV PORT=10000
EXPOSE $PORT
CMD ["./start.sh"]

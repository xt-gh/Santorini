#!/bin/bash
# Start X virtual framebuffer
export DISPLAY=:0
Xvfb :0 -screen 0 1024x768x24 -listen tcp -ac &

# Wait for Xvfb to start
sleep 2

# Start window manager
openbox-session &

# Start VNC server
x11vnc -display :0 -nopw -listen localhost -xkb -ncache 10 -ncache_cr -forever -shared &

# Make vnc.html the default page so it opens directly
ln -s /usr/share/novnc/vnc.html /usr/share/novnc/index.html

# Start noVNC web server on port $PORT (which Render will expose)
websockify --web /usr/share/novnc/ ${PORT:-10000} localhost:5900 &

# Run the Java application
java -cp /app/build/classes sprint3implementation.main.Application

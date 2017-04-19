#!/bin/bash

if [ ! -d /tmp/stream ]
then
mkdir /tmp/stream/
fi

if pgrep raspistill > /dev/null
then
echo "raspistill already running"
else
raspistill -w 640 -h 480 -q 5 -vf -o /tmp/stream/pic.jpg -tl 100 -t 9999999 -th 0:0:0 -n > /dev/null 2>&1&
echo "raspistill started"
fi

if pgrep mjpg_streamer > /dev/null
then
echo "mjpg_streamer already running"
else
LD_LIBRARY_PATH=/usr/local/lib mjpg_streamer -i "input_file.so -f /tmp/stream -n pic.jpg" -o "output_http.so -w /usr/local/www -p 8081" > /dev/null 2>&1&
echo "mjpg_streamer started"
fi

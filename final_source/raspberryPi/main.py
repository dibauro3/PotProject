# -*- coding: utf-8 -*-
import os
import sys
import serial
import MySQLdb
import datetime
import RPi.GPIO as GPIO
import time
import thread
from time import localtime, strftime

T = serial.Serial('/dev/ttyACM0', 9600)

ledPinNo = 23 #pinNo.23 = LED pinNo
pumpPinNo = 24 #pinNo.24 = Pump pinNo

lastFedDay = 0

led_state = 0

isItFirst = 1

watering = 0

waterLV_state = 0

LED_OnTime = 7.0
LED_OffTime = 20.0
Minimum_ill = 55
Minimum_moi = 300
pumpOnTimeV = 1.5

GPIO.setmode(GPIO.BCM) #Set GPIO
GPIO.setup(ledPinNo, GPIO.OUT) #LED for output
GPIO.setup(pumpPinNo, GPIO.OUT) #PUMP for output
GPIO.output(ledPinNo, False) #LED off
GPIO.output(pumpPinNo, False) #Pump off

def pumpOn(pumpOnTime):
  GPIO.output(pumpPinNo, True)
  time.sleep(pumpOnTime)
  GPIO.output(pumpPinNo, False)

while True:
  try:
    db = MySQLdb.connect(host='211.44.136.164', user='admin', passwd = 'skwldnjs',db='pot_project', port=3306)
    value = T.readline().split(',')
    moi=value[0]
    ill=value[1]
    temp=value[2]
    humi=value[3]
    waterLV=value[4]

    if(isItFirst==1):
      isItFirst=0
      continue
    with db:
      cur = db.cursor()
      if(value != None):
        print (value)
        cur.execute("INSERT INTO demo_test(date, moi, ill, temp, humi, waterLV, watering) VALUES (DEFAULT, %s, %s, %s, %s, %s, %s)" , (moi, ill, temp, humi, waterLV, watering))
      db.commit()

    now = datetime.datetime.now()
    nowTime = float(now.strftime('%H.%M')) #Time for LED
    ill_float = float(ill)
    moi_float = float(moi)
    waterLV_float = float(waterLV)
    toDay=float(now.strftime('%d'))

    if(waterLV_float!=waterLV_state):
      waterLV_state=waterLV_float
      if(waterLV_state==0):
        os.system('php push.php 1')


  #setting
    if(LED_OnTime<nowTime<LED_OffTime and ill_float<Minimum_ill):
      if(led_state==0):
        GPIO.output(ledPinNo, True)
        led_state=1
    else:
      if(led_state==1):
        GPIO.output(ledPinNo, False)
        led_state=0
    watering = 0
    if(moi_float<Minimum_moi and lastFedDay!=toDay):
      try:
        thread.start_new_thread(pumpOn, (pumpOnTimeV, ))
        lastFedDay=toDay
        watering = 1
        os.system('php push.php 2')
      except:
        print ("Push Error")
  except IndexError:
    print ("Data Error! Retrying")
    continue
  except KeyboardInterrupt: #When 'ctrl+c' clicked
    print ("Ended")
    GPIO.cleanup()
    exit()


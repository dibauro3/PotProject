# -*- coding: utf-8 -*-
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
    db = MySQLdb.connect(host='211.44.136.164', user='admin', passwd = 'skwldnjs',db='team_test', port=3306)
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
        print value
        cur.execute("INSERT INTO test4(date, moi, ill, temp, humi, waterLV) VALUES (now(), %s, %s, %s, %s, %s)" , (moi, ill, temp, humi, waterLV))
      db.commit()

    now = datetime.datetime.now()
    nowTime = float(now.strftime('%H.%M')) #Time for LED
    ill_float = float(ill)
    moi_float = float(moi)
    toDay=float(now.strftime('%d'))

  #setting
    if(7.0<nowTime<20.0 and ill_float<55):
      if(led_state==0):
        GPIO.output(ledPinNo, True)
        led_state=1
    else:
      if(led_state==1):
        GPIO.output(ledPinNo, False)
        led_state=0

    if(moi_float<700 and lastFedDay!=toDay):
      try:
        thread.start_new_thread(pumpOn, (2, ))
        lastFedDay=toDay
      except:
        print "Thread Error"
  except IndexError:
    print "Data Error! Retrying"
    continue
  except KeyboardInterrupt: #When 'ctrl+c' clicked
    print "Ended"
    GPIO.cleanup()
    exit()

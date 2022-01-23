from pynq.overlays.base import BaseOverlay 
from pynq_peripherals import ArduinoSEEEDGroveAdapter
base = BaseOverlay('base.bit')
from time import sleep
from requests import get
import json

def lighton():
    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, D7='grove_led_stick')
    led_stick = adapter.D7
    led_stick.clear()
    hexcolor = 0xFFFFFF

    for i in range(10):
        led_stick.set_pixel(i, hexcolor)
        led_stick.show()

def lightoff():
    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, D7='grove_led_stick')
    led_stick = adapter.D7
    led_stick.clear()

def doorOpen():
    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, D5='grove_servo')
    servo = adapter.D5
    servo.set_angular_position(90)

def doorClose():
    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, D5='grove_servo')
    servo = adapter.D5
    servo.set_angular_position(0)

while(True):
    json_array = get("https://redstone.thocraft.today/home/get_data.php").json()

    store_list = []

    for item in json_array:
        store_details = {"device":None, "state":None, "processed":None}
        store_details['device'] = item['device']
        store_details['state'] = item['state']
        store_details['processed'] = item['processed']
        store_list.append(store_details)

    #print(store_list)

    for k in store_list:
    #print(k['processed'] + k['device'] + k['state'])

        if k['processed'] == "0":
            if k['device'] == "light":
                if k['state'] == "0":
                    #print("light off")
                    lightoff()
                    get("https://redstone.thocraft.today/home/process_data.php?arg1=" + k['device'])

                else:
                    #print("light on")
                    lighton()
                    get("https://redstone.thocraft.today/home/process_data.php?arg1=" + k['device'])

            elif k['device'] == "door":
                if k['state'] == "0":
                    #print("door close")
                    doorClose()
                    get("https://redstone.thocraft.today/home/process_data.php?arg1=" + k['device'])

                else:
                    #print("door open")
                    doorOpen()
                    get("https://redstone.thocraft.today/home/process_data.php?arg1=" + k['device'])
    
    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, D6='grove_pir')
    motionsensor = adapter.D6
    
    if motionsensor.motion_detected():
        doorOpen()
    else:
        doorClose()

from pynq.overlays.base import BaseOverlay 
from pynq_peripherals import ArduinoSEEEDGroveAdapter
base = BaseOverlay('base.bit')
from time import sleep
from requests import get

def temp_water():
    
    print("Start running")
    
    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, A1='grove_temperature')
    temp_sensor = adapter.A1
    
    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, A3='grove_relay')
    relay = adapter.A3

    adapter = ArduinoSEEEDGroveAdapter(base.ARDUINO, D4='grove_water_sensor')
    water_sensor = adapter.D4
    
    temperature = temp_sensor.get_temperature()
    if temperature >= 35:
        relay.on()
    else:
        relay.off()
    # print('Temperature: {:.2f} degree Celsius'.format(temperature))
    if water_sensor.is_dry():
        water = "0"
    else:
        water = "1"
    get("https://redstone.thocraft.today/create_data.php?arg1=" + temperature + "&arg2=" + water)
    sleep(1)

while(True):
    temp_water()
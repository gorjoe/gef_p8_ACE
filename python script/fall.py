from pynq.overlays.base import BaseOverlay 
from pynq_peripherals import ArduinoSEEEDGroveAdapter
base = BaseOverlay('base.bit')
from time import sleep
from requests import get

def fall():
    adapter=ArduinoSEEEDGroveAdapter(base.ARDUINO, I2C='grove_imu')
    imu = adapter.I2C
    for i in range(20):
        imu.fetch_motion9()

        mag = sqrt(imu.accel_x ** 2 + (imu.accel_y ** 2) + (imu.accel_z ** 2))
        if mag >= 2.5:
            print("I fall down")
            r = get("https://maker.ifttt.com/trigger/felldown/with/key/gDYZoiH6aDIXOs5mBMbkUObSUXI58T9SnzdQNGnb9zS")
        else:
            print("im good")
        print(mag)
        sleep(1)

while(True):
    fall()
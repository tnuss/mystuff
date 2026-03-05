#include <Arduino.h>

//    =======================================
//    ===========### ESP32 CODE ###==========
//    =======================================

// put function declarations here:
int myFunction(int, int);
void blinkingLED();

// Define ADC Pin
const int adcReadPinA = 1; // GPIO1 (ADC1_CHANNEL_0)

// define pwm pin
const int PWMPIN_A = 2;          // GPIO 2
const int PWMFREQ_A = 5000;         // 5000 Hz
const int PWM_RESOLUTION_A = 8; 


//===================================================
void setup() {
  // initialize serial communication at 115200 bits per second:
  Serial.begin(115200);
  //rgbLedWrite(RGB_BUILTIN, 0, 0, RGB_BRIGHTNESS);  // Blue
  
    // NOTE: An ADC attenuation of '11db' is default, then: 0-3.3 volts is range (ESP32 specific)

    // NOTE2:  NEED a VOLTAGE DIVIDER if expecting voltages above 3.3v,  
    //         2 resistors of same value -> max input 6.6v, 
    //         higher resistance lower lost current Vout=(R1/R1+R2)Vin

  //set the resolution to 12 bits (0-4096)
  analogReadResolution(10);

  // Setup timer with given frequency, resolution and attach it to a led pin with auto-selected channel
  ledcAttach(PWMPIN_A, PWMFREQ_A, PWM_RESOLUTION_A);

  // Setup and start fade on led (duty from 0 to 4095)
  //ledcFade(PWMPIN_A, LEDC_START_DUTY, LEDC_TARGET_DUTY, LEDC_FADE_TIME);
  Serial.println("Check PWM voltage started.");
}

//===================================================
void loop() {

      // show that code is working
  blinkingLED();

  // Fade in: pin, start_duty, target_duty, time_ms
  //ledcFade(PWMPIN_A, 0, 255, 30000);
  analogWrite(PWMPIN_A, 255); // just run pwm at a certain level
  delay(2000);

  // read the analog / millivolts value for pin 2:
  int analogValue = analogRead(adcReadPinA);
  float analogVolts = analogReadMilliVolts(adcReadPinA) / 1000.0;
  // print out the values you read:
  Serial.printf("ADC Raw: %d\n",analogValue);
  Serial.printf("ADC V:  %f\n",analogVolts);
  
  // Fade out
  //ledcFade(PWMPIN_A, 255, 0, 2000);
  //analogWrite(PWMPIN_A, 0);
  //delay(2000);
  // print out the values you read:
  
  // read the analog / millivolts value for pin 2:
  analogValue = analogRead(adcReadPinA);
  analogVolts = analogReadMilliVolts(adcReadPinA);
  
  // print out the values you read:
  Serial.printf("ADC Raw2: %d\n",analogValue);
  Serial.printf("ADC mV2:  %d\n",analogVolts);
  
  delay(100);  // delay in between reads for clear read from serial
}

void blinkingLED() {

  rgbLedWrite(RGB_BUILTIN, 0, 5, 0);  // Green
 // delay(1000);

  delay(250);
  rgbLedWrite(RGB_BUILTIN, 0, 0, 0);  // Off / black
  //delay(1000);

}
// put function definitions here:
int myFunction(int x, int y) {
  return x + y;
}

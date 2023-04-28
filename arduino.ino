#include <PulseSensorPlayground.h>
#include <Arduino_LSM6DS3.h>
#include <Firebase_Arduino_WiFiNINA.h>

#define pinNumberSensor 0  
#define sizeOfSamp 4 
#define increaseOfThres 5    
#define nameOfWiFi "Bedroom"    
#define nameOfPassword ""
#define hostURL "finalyearproject-183e6-default-rtdb.firebaseio.com"
#define authURL "hCuH5Ek9cIAQmKwpKdAvYJvZ2h7y6BgFzuqazUUL"

FirebaseData saadFirebase;  
String path = "/HeartSensor";

void sensorFunc() {
  Serial.print("Sensor..... detecting......");
  if (!IMU.begin()) {
    Serial.println("It has failed! Try again please.......");
    for (;;) ;
  }
  Serial.println(" Dealt with! ");
  Serial.print("Heart Rate Data is : ");
}

void wifiFunc() {
  Serial.print("Trying to connect to the WiFi connection......");
  int theUpdate = WL_IDLE_STATUS;
  while (theUpdate != WL_CONNECTED) {
    theUpdate = WiFi.begin(nameOfWiFi, nameOfPassword);
    delay(300);
  }
  Serial.print(" IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
}

void firebaseFunc() {
  Firebase.begin(hostURL, authURL, nameOfWiFi, nameOfPassword);
  Firebase.reconnectWiFi(true);
}

void setup() {
  Serial.begin(9600);
  delay(500);
   Serial.println();
   sensorFunc();  
   wifiFunc();
   firebaseFunc();
 
}

void loop() {
 heartRateData();
}

void heartRateData(){
  
  float gett[sizeOfSamp], theTotal = 0, finalVal = 0; //
  long initial = 0, pointAt = 0, endingBeat = 0; //
  float position_one = 0, position_two = 0, position_three = 0, earlier = 0, displayTheValue = 0;
  bool increasingValue = false;
  int counterForIncrease = 0, v = 0;
  int i = 0;
  while (i < sizeOfSamp) {
    gett[i] = 0;
    i = i + 1;
  }

   for (;;) {
    v = 0;
    float valueOfSubscriber = 0;
    long startStage = millis();

    do {
      valueOfSubscriber += analogRead(pinNumberSensor);
      v++;
      initial = millis();
    } while (initial < startStage + 20);

    valueOfSubscriber /= v;
    theTotal -= gett[pointAt];
    theTotal += valueOfSubscriber;
    gett[pointAt] = valueOfSubscriber;
    finalVal = theTotal / sizeOfSamp;

    if (finalVal > earlier) {
      counterForIncrease++;

      if (!increasingValue && counterForIncrease > increaseOfThres) {
        increasingValue = true;
        position_one = millis() - endingBeat;
        endingBeat = millis();
        displayTheValue = 58000. / (0.51 * position_one + 0.41 * position_two + 0.41 * position_three);

        Serial.println("Heart Beat is :::");
        Serial.println(displayTheValue);

        if (Firebase.setFloat(saadFirebase, path + "/heartData", displayTheValue)) {
         
        }

        position_three = position_two;
        position_two = position_one;
      }
    } else {
      increasingValue = false;
      counterForIncrease = 0;
    }

    earlier = finalVal;
    pointAt++;
    pointAt %= sizeOfSamp;
  }

}

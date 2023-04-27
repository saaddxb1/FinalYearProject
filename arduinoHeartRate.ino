#include <PulseSensorPlayground.h>

#include <PulseSensorPlayground.h>


#define theSize 4
#define rise_threshold 5
// Pulse Monitor Test Script
int sensorPin = 0;
#include <Arduino_LSM6DS3.h>
#include <Firebase_Arduino_WiFiNINA.h>

#define hostOfFirebase "finalyearproject-183e6-default-rtdb.firebaseio.com"
#define authOfFirebase "hCuH5Ek9cIAQmKwpKdAvYJvZ2h7y6BgFzuqazUUL"
#define WiFiName "Bedroom"
#define WiFiPassword ""

//MDX welcomes you
//MdxL0vesyou

FirebaseData saadFirebase;

String path = "/HeartSensor";


void setup()
{
  Serial.begin(9600);
  delay(2000);
  Serial.println();

  Serial.print("Sensor.....");
  if (!IMU.begin()) {
    Serial.println("It has failed! Try again.");
    for (;;);
  }
  Serial.println(" Dealt with! ");
  Serial.print("Heart Rate Data = ");


  Serial.print("Trying to connect to the WiFi...... :)");
  int theUpdate = WL_IDLE_STATUS;
  while (theUpdate != WL_CONNECTED) {
    theUpdate = WiFi.begin(WiFiName, WiFiPassword);
    delay(200);
  }
  Serial.print(" IP is: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(hostOfFirebase, authOfFirebase, WiFiName, WiFiPassword);
  Firebase.reconnectWiFi(true);
}

void loop()
{
// the variables used 

  float gett[theSize]; 
  float addition; 
  long int currently; 
  long int rate; 
  float finalV; 
  float parho;
  float initial; 
  float position_one; 
  float position_second; 
  float position_third; 
  float thePast; 
  float heartRateReading; 
  bool increasing; 
  int increasingCounter; 
  int u;
  long int endingBeat; 
  int z = 0;
// the logic

  while(z<theSize){
    gett[z] = 0;
    z = z + 1;
  }
  
//  for (int z = 0; z < theSize; z++)
//    gett[z] = 0;
    
  addition = 0;
  rate = 0;
  
//  for(a in 0..userSensorData.size-1){
//            if(a in emptyList){
//                userSensorData[a] = -1
//            }
//            var isTemp = a
//            for(j in 0..userSensorData.size-1){
//                if(j in emptyList){
//
//                }
//                else{
//                    if(userSensorData[j]>userSensorData[isTemp]){
//                        isTemp = j
//                    }
//                }
//            }

  
  for (;;)
  {

    u = 0;
    parho = 0.0;
    initial = millis();
    do
    {
      parho += analogRead (sensorPin);
      u++;
      currently = millis();
    }
    while (currently < initial + 21);
    parho /= u; 
    addition -= gett[rate];
    addition += parho;
    gett[rate] = parho;
    finalV = addition / theSize;

    if (finalV > thePast)
    {
      increasingCounter++;
      if (!increasing && increasingCounter > rise_threshold)
      {
    
        position_one = millis() - endingBeat;
        increasing = true;
        endingBeat = millis();
  
        heartRateReading = 60010. / (0.5 * position_one + 0.4 * position_second + 0.4 * position_third);
        
        Serial.println(heartRateReading);
        if (Firebase.setFloat(saadFirebase, path + "/heartData", heartRateReading)) {
          Serial.println(saadFirebase.dataPath() + " = " + heartRateReading);
        }
        position_third = position_second;
        position_second = position_one;
      }
    }
    
//    if (running) {
//            if (op != null) {
//       
//                tv_stepsTaken.text = "0"
//                totalSteps = op.values[2]
//            }
//            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
//            tv_stepsTaken.text = "$currentSteps"

    else
    {
      increasingCounter = 0;
      increasing = false;
    }
    thePast = finalV;
    rate++;
    rate %= theSize;
  }}

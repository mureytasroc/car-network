#include <Servo.h>
#include "Adafruit_VL53L0X.h"
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WiFiMulti.h>
#include <ArduinoOTA.h>
#include <ESP8266mDNS.h>
#include <FS.h>
#include <Wire.h>

/*
 * DATA:
 * 22.82 pulse/in
 * long path:40" = 913
 * short path: 24" = 548
 * 
 * 
 */

#include <Adafruit_TCS34725.h>

#define TCAADDR 0x70


#define MOTORA_WRITE 0x64
#define MOTORA_READ 0x65

#define CONTROL_addr 0x00
#define FAULT_addr 0x01


Servo turn;

int encoder_pin = 13; 


boolean readColor = true;
unsigned long tcsMillis = 0;
uint16_t clear, red, green, blue;

unsigned long ts = 0;

Adafruit_TCS34725 tcs = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_2_4MS, TCS34725_GAIN_60X);

Adafruit_VL53L0X lox = Adafruit_VL53L0X();

const char *carName = "car1";
#define R2 1642
#define R1 9710

const char *OTAName = carName;           // A name and a password for the OTA service
const char *OTAPassword = carName;

const char* host = "192.168.1.148";

WiFiClient client;


const char *mdnsName = carName; // Domain name for the mDNS responder

ESP8266WiFiMulti wifiMulti;

float voltage;

String directions[40];
int dirIndex=0;
boolean GR;

void setup() {

  directions[0]="tlequation";
  directions[1]="tlequation";
  directions[2]="flequation";
  directions[3]="flequation";
  GR=(directions[dirIndex].charAt(0)=='t');

  Wire.begin();
  Serial.begin(115200);        // Start the Serial communication to send messages to the computer
  delay(10);
  Serial.println("\r\n");

  coast();


  startWiFi();                 // Start a Wi-Fi access point, and try to connect to some given access points. Then wait for either an AP or STA connection

  startMDNS();                 // Start the mDNS responder

  startOTA();                  // Start the OTA service

  turn.attach(14);
  turn.write(90);

  tcaselect(6);
  if (tcs.begin()) {
    Serial.println("Found sensor");
  } else {
    Serial.println("No TCS34725 found ... check your connections");
    while (true) {
      Serial.println("NO TCS");
      ArduinoOTA.handle();
      yield();
      // halt!
    }

  }

  Serial.println("Adafruit VL53L0X test");
  tcaselect(3);
  if (!lox.begin()) {
    Serial.println(F("Failed to boot VL53L0X"));
    while (true) {
      Serial.println("NO VL53L0X");
      ArduinoOTA.handle();
      yield();
    }
  }

  voltage = (float)analogRead(0) * (R1 + R2) / (R2 * 1024);

  pinMode(encoder_pin, INPUT);
  attachInterrupt(encoder_pin,do_count,RISING);

  /*Serial.println();
    Serial.print("My IP: ");
    Serial.println(WiFi.localIP());
    long rssi = WiFi.RSSI();
    Serial.print("RSSI: ");
    Serial.print(rssi);
    Serial.println(" dBm");*/



  //client.setNoDelay(true);
}
//int counter = 0;
unsigned int encPos=0;
long encFloor = 0;
float distTraveled;
float turnPos = 90;
float motorVal = 0;
VL53L0X_RangingMeasurementData_t measure;
float distance;
boolean stopp=false;


unsigned long ts2 = 0;

boolean colorTriggered = true;
long colorStartDist=-1;
unsigned long turnDistStart = 0;


void loop() {
  collectSensorData();
  /*while(voltage<5.2){
    Serial.print("VOLTAGE ");
    Serial.println(voltage);
    ArduinoOTA.handle();
    drive(0);
    yield();
    }*/
  /*if (distTraveled < 460) { //rightAngle
    drive(20);
    }
    else {
    drive(0);
    }
    turnPos = 20;*/
  //Serial.println("loop");
  //delay(20);
  //turn.write(90);//20-160//center87
  //delay(20);

  ArduinoOTA.handle();



  if(!stopp){//startb
  if (!colorTriggered) {
    if (encPos - turnDistStart < 550) {//turn
      drive(20);
    }
    else {
      brake();
      turnPos=90;
      colorTriggered = true;
      colorStartDist=-1;
      dirIndex++;
      GR=(directions[dirIndex].charAt(0)=='t');
    }
    }
    else {
    if (blue < 120) {
      if(directions[dirIndex]==""){
        stopp=true;
      }
      else{
      drive(15);
      followLine();}
    }
    else {
      //brake();
      if(colorStartDist==-1){
        colorStartDist=encPos;
      }
      else{
        if(encPos-colorStartDist>30){
          if(directions[dirIndex].charAt(1)=='l'){turnPos = 20;}else if(directions[dirIndex].charAt(1)=='l'){turnPos = 160;} else{stopp=true;}
          colorTriggered = false;
          turnDistStart = encPos;
        }
        else{
          drive(15);
          followLine();
        }
      }
      
    }
    }
  }
  else{
    turnPos=90;
    brake();
  }//end
    

  /*if (encPos - turnDistStart < 600) {
    drive(25);
    turnPos = 20;
  }
  else {
    turnPos = 90;
    //brake();
  }

  followLine();
  drive(20);*/
  //coast();





  /*getColor();
    Serial.print("R: "); Serial.println(red);
    Serial.print("G: "); Serial.println(green);
    Serial.print("B: "); Serial.println(blue);
    Serial.print("Clear: "); Serial.println(clear);*/



  /*Serial.print("R: "); Serial.print(String(red));
    Serial.print("  G: "); Serial.print(String(green));
    Serial.print("  B: "); Serial.print(String(blue));
    Serial.println("...   ");*/



/*followLine();

  if (client.connected()) {

    if (millis() - ts > 500) {
      ts = millis();
      sendMessage("RG: "); sendMessage(String(red - green));
      sendMessage("  B: "); sendMessage(String(blue));
      sendMessage("...   ");
    }

    Serial.print(getMessage());
    }
    else {
    if (!client.connect(host, 3000)) {
      //ArduinoOTA.handle();
      Serial.print("NO_CONNECT");
      //yield();
    }
    }*/

  control();


}

void collectSensorData() {



  getColor();


}

void encChanged() {
  //if(client.connected()){
  //sendMessage(String(distTraveled));}

}

void followLine() {
  if(GR){
  turnPos = map(red - green, -40, 230, 114, 70);}
  else{
  turnPos = map(red - green, -40, 230, 70, 114);
  }
  turnPos = constrain(turnPos, 70, 114);
  //turn.write(90);//20-160//center87
}

void control() {
  turn.write(turnPos);

}


void closeConnection() {
  Serial.println("\n\nClosing connection");
  client.flush();
  client.stop();
  Serial.println("Connection Closed\n");
}


String getMessage() {
  if (client.available()) {
    String line = "";
    line = client.readStringUntil('\n');
    return line;
  }
  return "";
}

void sendMessage(String msg) {
  String out = msg + "\nend";
  client.println(msg);
}

void startWiFi() { // Start a Wi-Fi access point, and try to connect to some given access points. Then wait for either an AP or STA connection

  wifiMulti.addAP("DVW3201BE8", "DVW3201BB69FE8");   // add Wi-Fi networks you want to connect to
  wifiMulti.addAP("Trinity", "wirelesskey");

  Serial.println("Connecting ...");

  while (wifiMulti.run() != WL_CONNECTED) { // Wait for the Wi-Fi to connect: scan for Wi-Fi networks, and connect to the strongest of the networks above
    delay(250);
    Serial.print('.');
  }
  Serial.println('\n');
  Serial.print("Connected to ");
  Serial.println(WiFi.SSID());              // Tell us what network we're connected to
  Serial.print("IP address:\t");
  Serial.println(WiFi.localIP());           // Send the IP address of the ESP8266 to the computer
}

void startMDNS() { // Start the mDNS responder
  MDNS.begin(mdnsName);                        // start the multicast domain name server
  Serial.print("mDNS responder started: http://");
  Serial.print(mdnsName);
  Serial.println(".local");
}


void startOTA() { // Start the OTA service
  ArduinoOTA.setHostname(OTAName);
  //ArduinoOTA.setPassword(OTAPassword);

  ArduinoOTA.onStart([]() {
    Serial.println("Start");
  });
  ArduinoOTA.onEnd([]() {
    Serial.println("\nEnd");
  });
  ArduinoOTA.onProgress([](unsigned int progress, unsigned int total) {
    Serial.printf("Progress: %u%%\r", (progress / (total / 100)));
  });
  ArduinoOTA.onError([](ota_error_t error) {
    Serial.printf("Error[%u]: ", error);
    if (error == OTA_AUTH_ERROR) Serial.println("Auth Failed");
    else if (error == OTA_BEGIN_ERROR) Serial.println("Begin Failed");
    else if (error == OTA_CONNECT_ERROR) Serial.println("Connect Failed");
    else if (error == OTA_RECEIVE_ERROR) Serial.println("Receive Failed");
    else if (error == OTA_END_ERROR) Serial.println("End Failed");
  });
  ArduinoOTA.begin();
  Serial.println("OTA ready");
}



void drive(int speed) {
  if (millis() - ts2 > 40) {

    ts2 = millis();
    // Write to the Fault Register to reset it
    Wire.beginTransmission(MOTORA_WRITE);
    Wire.write(FAULT_addr);
    Wire.write(0x80);
    Wire.endTransmission(true);

    // Write to the Control Register
    Wire.beginTransmission(MOTORA_WRITE);
    Wire.write(CONTROL_addr);
    byte regValue;
    regValue = (byte)abs(speed); // Find the byte-ish abs value of the input
    if (regValue > 63) regValue = 63;
    regValue = regValue << 2; // Left shift to make room for bits 1:0
    if (speed < 0) regValue |= 0x01; // Set bits 1:0 based on sign of input.
    else regValue |= 0x02;
    Wire.write(regValue);
    Wire.endTransmission(true);

    // // Read Fault Register
    // Wire.beginTransmission(MOTORA_WRITE);
    // Wire.write(FAULT_addr);
    // //Wire.endTransmission(I2C_NOSTOP);
    // Wire.requestFrom(MOTORA_READ,1,I2C_STOP);
    //
    // while(Wire.available()) { Serial.printf("%d ", Wire.readByte()); }

    //delay(100);
  }
}
void brake() {

  Wire.beginTransmission(MOTORA_WRITE);
  Wire.write(FAULT_addr);
  Wire.write(0x80);
  Wire.endTransmission(true);

  Wire.beginTransmission(MOTORA_WRITE);
  Wire.write(CONTROL_addr);
  byte regValue = (byte)63;
  regValue = regValue << 2;
  regValue |= 0x03;
  Wire.write(regValue);
  Wire.endTransmission(true);

  //delay(100);
}

void coast() {
  Wire.beginTransmission(MOTORA_WRITE);
  Wire.write(FAULT_addr);
  Wire.write(0x80);
  Wire.endTransmission(true);




  Wire.beginTransmission(MOTORA_WRITE);
  Wire.write(CONTROL_addr);
  byte regValue = (byte)0;
  //regValue = regValue << 2;
  //regValue &= 0xFC;//set first two bits to be 0
  Wire.write(regValue);
  Wire.endTransmission(true);
}


void tcaselect(uint8_t i) {
  if (i > 7) return;

  Wire.beginTransmission(TCAADDR);
  Wire.write(1 << i);
  Wire.endTransmission();
}

void getColor() {

  if (readColor) {

    //tcaselect(3);
    //lox.rangingTest(&measure, false);

    readColor = false;
    tcaselect(6);
    tcs.setInterrupt(false);      // turn on LED
    tcsMillis = millis();
  }  // takes 50ms to read
  if (millis() - tcsMillis > 2.5) {
    tcaselect(6);
    tcs.getRawData(&red, &green, &blue, &clear);
    tcaselect(6);
    tcs.setInterrupt(true);
    readColor = true;

    /*tcaselect(3);
      if (measure.RangeStatus != 4) {
      distance = measure.RangeMilliMeter;
      }
      else {
      distance = -1;
      }*/


  }

}

void do_count() {
  encPos++;
}


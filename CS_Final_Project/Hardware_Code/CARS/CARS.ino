#include "Adafruit_VL53L0X.h"
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WiFiMulti.h>
#include <ArduinoOTA.h>
#include <ESP8266mDNS.h>
#include <FS.h>
#include <Wire.h>

#include <Adafruit_TCS34725.h>

#define TCAADDR 0x70

#define MOTORA_WRITE 0x64
#define MOTORA_READ 0x65

#define CONTROL_addr 0x00
#define FAULT_addr 0x01

#define TESTCASE 0xFE // b11111110

int timeDelay = 250;

uint16_t clear, red, green, blue;

Adafruit_TCS34725 tcs = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_2_4MS, TCS34725_GAIN_1X);

Adafruit_VL53L0X lox = Adafruit_VL53L0X();

const char *carName = "car1";

const char *OTAName = carName;           // A name and a password for the OTA service
const char *OTAPassword = carName;

const char* host = "192.168.1.148";

WiFiClient client;


const char *mdnsName = carName; // Domain name for the mDNS responder

ESP8266WiFiMulti wifiMulti;

void setup() {

  Wire.begin();
  Serial.begin(115200);        // Start the Serial communication to send messages to the computer
  delay(10);
  Serial.println("\r\n");


  /*Serial.println("Adafruit VL53L0X test");
  tcaselect(3);
  if (!lox.begin()) {
    Serial.println(F("Failed to boot VL53L0X"));
    while(1);
  }*/

  
   tcaselect(6);
  if (tcs.begin()) {
    Serial.println("Found sensor");
  } else {
    Serial.println("No TCS34725 found ... check your connections");
    while (1); // halt!
  }

  startWiFi();                 // Start a Wi-Fi access point, and try to connect to some given access points. Then wait for either an AP or STA connection

  startMDNS();                 // Start the mDNS responder

  //startSPIFFS();               // Start the SPIFFS and list all contents

  startOTA();                  // Start the OTA service




  /*Serial.println();
    Serial.print("My IP: ");
    Serial.println(WiFi.localIP());
    long rssi = WiFi.RSSI();
    Serial.print("RSSI: ");
    Serial.print(rssi);
    Serial.println(" dBm");*/

  /*while (!client.connect(host, 3000)) {
    ArduinoOTA.handle();
    Serial.print("/");
    yield();
  }*/

  //client.setNoDelay(true);
}
int counter = 0;

void loop() {

  

  ArduinoOTA.handle();

  /*for (int speedValue = 0 ; speedValue <= 63; speedValue += 5) {
    drive(speedValue);
    Serial.println(speedValue);
    delay(timeDelay);
  }*/
  getColor();
  Serial.print("R: "); Serial.println(red);
  Serial.print("G: "); Serial.println(green);
  Serial.print("B: "); Serial.println(blue);
  Serial.print("Clear: "); Serial.println(clear);
  
  
 
 
 
  //Serial.print("hell");

  /*if(client.connected()){



    sendMessage("sup");

    Serial.print(getMessage());
    delay(500);
    }
    else{
    while(!client.connect(host,3000)){
      ArduinoOTA.handle();
    Serial.print("/");
    yield();
    }
    }


    yield();*/
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

void startSPIFFS() { // Start the SPIFFS and list all contents
  SPIFFS.begin();                             // Start the SPI Flash File System (SPIFFS)
  Serial.println("SPIFFS started. Contents:");
  {
    Dir dir = SPIFFS.openDir("/");
    while (dir.next()) {                      // List the file system contents
      String fileName = dir.fileName();
      size_t fileSize = dir.fileSize();
      Serial.printf("\tFS File: %s, size: %s\r\n", fileName.c_str(), formatBytes(fileSize).c_str());
    }
    Serial.printf("\n");
  }
}

void startOTA() { // Start the OTA service
  ArduinoOTA.setHostname(OTAName);
  ArduinoOTA.setPassword(OTAPassword);

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



//=================HELPER FUNCTIONS===========================

String formatBytes(size_t bytes) { // convert sizes in bytes to KB and MB
  if (bytes < 1024) {
    return String(bytes) + "B";
  } else if (bytes < (1024 * 1024)) {
    return String(bytes / 1024.0) + "KB";
  } else if (bytes < (1024 * 1024 * 1024)) {
    return String(bytes / 1024.0 / 1024.0) + "MB";
  }
}

void drive(int speed) {
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

  delay(100);
}

void tcaselect(uint8_t i) {
  if (i > 7) return;
 
  Wire.beginTransmission(TCAADDR);
  Wire.write(1 << i);
  Wire.endTransmission();  
}

void getColor(){

  tcaselect(6);
  tcs.setInterrupt(false);      // turn on LED
  
  delay(2.5);  // takes 50ms to read

  tcaselect(6);
  tcs.getRawData(&red, &green, &blue, &clear);

  tcaselect(6);
  tcs.setInterrupt(true);
  
}


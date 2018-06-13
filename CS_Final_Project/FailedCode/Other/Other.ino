#include <ESP8266WiFi.h>
#include <ArduinoJson.h>
#include "FS.h"


const char* ssid     = "DVW3201BE8";       // insert your SSID
const char* password = "DVW3201BB69FE8";  // insert your password
const int httpPort = 1201;                // Setting the communication port
String passingValue = "";
const char* host = "192.168.1.148";
String url = "/VarghaHokmran";
String varOne;        // First variable that we are working with
String varTwo;        // Second variable that we are working with

//There are two memory banks in the LCD, data/RAM and commands. This function
// sets the DC pin high or low depending, and then sends the data byte




//This takes a large array of bits and sends them to the LCD




/* -------------------------------------------- SETUP --------------------------------------------*/
void setup() {
  
  Serial.begin(115200);
  Serial.println("Connecting  ");
  Serial.println("to WiFi");
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  

  Serial.print("    WiFi    "); 
  Serial.println(" Connected! "); 
  delay(1000);

}

/* -------------------------------------------- LOOP --------------------------------------------*/
void loop() {

  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  //const int httpPort = 1201;                // connection port. 80 for http
  if (!client.connect(host, httpPort)) {
    return;
  }

// Here, you can put any variable that you would like to pass to PC
  passingValue = "1234";

  // This will send the request to the server
  client.print(String("GET ") + url /*+ key*/ + passingValue + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  delay(10);
/**************************************/
  unsigned int i = 0; //timeout counter
  int n = 1; // char counter
  char json[500] ="{";
  while (!client.find("myData: ")){} 
  while (i<600) {
    if(client.available()) {
      char c = client.read();
      json[n]=c;                      // READ $ PRICE CHARACTERS INTO JSON ARRAY
      if(c=='}') break;
      n++;
      i=0;
    }
    i++;
  }

  StaticJsonBuffer<500> jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(json);

  if (!root.success()) {
    return;
  }

  
  Serial.print("   Please");
  Serial.print("     &");
  varOne = root["otherText"].asString();  

  char charUSD[50];
  varOne.toCharArray(charUSD, 50);

  Serial.println(charUSD);
/**************************************/
/**************************************/
  i = 0; // timeout counter
  n = 1; // char counter
  while (i<600) {
    if(client.available()) {
      char c = client.read();
      json[n]=c;
      if(c=='}') break;
      n++;
      i=0;
    }
    i++;
  }

  StaticJsonBuffer<500> jsonBuffer2;
  JsonObject& root2 = jsonBuffer2.parseObject(json);
  if (!root.success()) {
    return;
  }
  varTwo = root["myText"].asString();  

  char charName[50];
  varTwo.toCharArray(charName, 50);
  
  Serial.println(charName);
/**************************************/

  Serial.println("Updated!    ");

  

  varOne ="";
  //varTwo ="";
}

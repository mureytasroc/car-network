#include<ESP8266WiFi.h>

const char* ssid = "DVW3201BE8";    // This is Your Wifi SSID
const char* pwd = "DVW3201BB69FE8";   //This is Your WIFi Hotspot Password

WiFiServer server(5000);           // 5000 is Your Port Number on which communication take place
char data[250], dcr[250];          // For Data Recieve / Send
int ind = 0 , icd=0;

WiFiClient client;                //Socket get Connected through the Server

void setup() {
  Serial.begin(115200);             //For Arduino Serial Communication
  delay(10);
  WiFi.mode(WIFI_STA);            //WIFI Mode
  WiFi.begin(ssid,pwd);           //WiFi Setup
  Serial.print("Connecting");     //Arduino Will Print This Message
  while(WiFi.status()!=WL_CONNECTED){ //Until it get connected
    delay(500);
  }
  server.begin();                 //Server Will Start Listening
  //Serial.print("Connected");
  Serial.println(WiFi.localIP());
}

void loop() {
  if(!client.connected()){    // Try to get a Client
    client= server.available();
  }
  else{                     // When Client is Connected
    if(Serial.available()>0){
      while(Serial.available()){
        dcr[icd] = Serial.read();   // Get Data From Arduino to Buffer
        icd++;
      }
      for(int j=0;j<icd;j++){
        client.print(dcr[j]);       //printing the Recieve data from ardunio to WIFI Socket
      }
    }
    if(client.available()>0){
      while(client.available()){     //run unitl data is available for reading
        data[ind] = client.read();    //reading data
        ind++;
      }
      client.flush();
      for(int j=0;j<ind;j++){
        Serial.print(data[j]);      //printing/Sending Data to Ardunio
      }
    }
    icd=0;            //reset the buffer counter
    ind=0;            //reset the buffer counter
  }
}

//  DRV8830 test / ESP-WROOM-02
//
//  DRV8830で直流モータをDriveするテスト。速度を正弦波に乗せてゆっくり加減速と反転を繰り返す
//
//  Thanks :
//    「Arduino Nano とモータードライバでDCモーターを正転・逆転してみた（１）　[Arduino]」
//    http://makers-with-myson.blog.so-net.ne.jp/2014-05-15
//

//
//  Wire / DRV8830
//
#include <Wire.h>


const int kDrv8830Address = 0x64;

const int kBitClear  = 0x80;
const int kBitILimit = 0x10;
const int kBitOTS    = 0x08;
const int kBitUVLO   = 0x04;
const int kBitOCP    = 0x02;
const int kBitFault  = 0x01;


//
//  Setup
//

void setup() {
  pinMode(16,INPUT);
  Serial.begin(115200);
  Wire.begin(5,4);
}


//
//  Loop
//

float r = 0;

void loop() {
  //
  //  Motor
  float s = sin(r) * 64.0;
  r += 0.1;
  if (r > 6.28) r = 0.0;

  int out = (s > 0) ? 0x01 : 0x02;
  int speed = s;

  runMotor(speed);

  int status = readMotorStatus();
  if (status & kBitFault) {
    Serial.print("Motor Fault : ");
    Serial.println(status, HEX);
    resetMotorStatus();
  }
  
  delay(100);
}


//
//  DRV8830 Controll
//
uint8_t readMotorStatus() {
  uint8_t result = 0x00;
    
  Wire.beginTransmission(kDrv8830Address);
  Wire.write(0x01); //  read register 0x01
  Wire.endTransmission();

  Wire.requestFrom(kDrv8830Address, 1);
  if (Wire.available()) {
    result = Wire.read();
  } else {
    Serial.println("No status data");
  }

  return result;
}

void resetMotorStatus() {
  Wire.beginTransmission(kDrv8830Address);
  Wire.write(0x01); //  fault
  Wire.write(0x80);  // clear
  Wire.endTransmission(true);
}


void runMotor(int inVector) {
  int direction;
  int voltage;

  if (inVector > 0) {
    direction = 0x01;
    voltage = inVector;
  } else if (inVector == 0) {
    direction = 0x00;
    voltage = 0;
  } else {
    direction = 0x02;
    voltage = -inVector;
  }

  writeToDriver(direction, voltage);
}

void brakeMotor() {
  writeToDriver(0x03, 0x00);
}


void writeToDriver(byte inDirection, byte inVoltage) {
  if (inVoltage <= 0x05) inVoltage = 0x06;  // minimum voltage valu is 0x06.
  
  int outData = (inVoltage & 0x3f) << 2 | (inDirection & 0x03);
  Wire.beginTransmission(kDrv8830Address);
  Wire.write(0x00); //  control
  Wire.write(outData);  //
  Wire.endTransmission(true);

  delay(12);
}

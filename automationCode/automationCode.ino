#include <WiFi.h>
#include <FirebaseESP32.h>
#include <dht11.h>

#define FIREBASE_HOST "xxxx"  //Write Firebase Host of your Firebase Database here
#define FIREBASE_AUTH "xxxx"  //Write Firebase Auth of your Firebase Database here
#define WIFI_SSID "xxxx"      //Write SSID of your Local Internet here
#define WIFI_PASSWORD "xxxx"  //Write Password of your Local Internet here

FirebaseData firebaseData;
dht11 DHT11; 

#define pirPin 35   // PIR sensor
#define dhtPin 14   // DHT11
#define ldrPin 33   // LDR
#define relay1 2    // Relay 1
#define relay2 4    // Relay 2
#define relay3 18   // Relay 3
#define relay4 19   // Relay 4
#define relayL 22   // Relay Light
#define relayF 23   // Relay Fan

int noti;  // Variable to store value of PIR sensor
int temp;  // Variable to store value of Temperature
int hum;   // Variable to store value of Humidity
int light; // Variable to store value of LDR sensor
int tog;   // Variable to store value of toggle from firebase
int varRTemp,var1,var2,var3,var4;
int a1,a2,a3,a4;

void setup() {
  Serial.begin(115200);
  pinMode(pirPin, INPUT);
  pinMode(dhtPin, INPUT);
  pinMode(ldrPin, INPUT);
  pinMode(relay1, OUTPUT);
  pinMode(relay2, OUTPUT);
  pinMode(relay3, OUTPUT);
  pinMode(relay4, OUTPUT);
  pinMode(relayL, OUTPUT);
  pinMode(relayF, OUTPUT);

  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.println("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.enableClassicRequest(firebaseData, true);

}

void loop() {
  roomTemperature();
  roomLight();
  contolAppliances();
  intruderAlarm();
}

void roomTemperature(){
  DHT11.read(dhtPin);
  temp = DHT11.temperature; 
  hum = DHT11.humidity;;
  Firebase.setInt(firebaseData,"/Monitor/Humidity", hum);
  Firebase.setInt(firebaseData,"/Monitor/Temperature", temp);
  Firebase.getInt(firebaseData,"/Toggle");
  var1 = firebaseData.intData(); 
  if(var1 == 0)
  {
    Firebase.getInt(firebaseData,"/Control/Fan");
    var2 = firebaseData.intData();
    if(var2 == 1)
    {
      Serial.println("Fan is on");
      digitalWrite(relayF,HIGH);
    }
    else if (var2 == 0)
    {
      Serial.println("Fan is off");
      digitalWrite(relayF,LOW);
    }
  }
  else
  {
    if(temp>=26)
    {
      Serial.println("Fan is on");
      digitalWrite(relayF,HIGH);
    }
    else
    {
      Serial.println("Fan is off");
      digitalWrite(relayF,LOW);
    }
  }
  delay(500);
}

void roomLight(){
  light = digitalRead(ldrPin);
  Firebase.getInt(firebaseData,"/Toggle");
  var3 = firebaseData.intData();
  if(var3 == 0)
  {
    Firebase.getInt(firebaseData,"/Control/Light");
    var4 = firebaseData.intData();
    if(var4 ==  1)
    {
      Serial.println("Light is on");
      digitalWrite(relayL,HIGH);
    }
    else if (var2 == 0)
    {
      Serial.println("Light is off");
      digitalWrite(relayL,LOW);
    }
  }
  else
  {
    if(light == 1)
    {
      Serial.println("Light is on");
      digitalWrite(relayL,HIGH);
    }
    else
    {
      Serial.println("Light is off");
      digitalWrite(relayL,LOW);
    }
  }
  delay(500);
}

void contolAppliances(){
  Firebase.getInt(firebaseData,"/Control/App1");
  a1 = firebaseData.intData();
  if (a1 == 0){
    Serial.println("Appliance 1 is off");
    digitalWrite(relay1,LOW);
  }else{
    Serial.println("Appliance 1 is on");
    digitalWrite(relay1,HIGH);
  }
  
  Firebase.getInt(firebaseData,"/Control/App2");
  a2 = firebaseData.intData();
  if (a2 == 0){
    Serial.println("Appliance 2 is off");
    digitalWrite(relay2,LOW);
  }else{
    Serial.println("Appliance 2 is on");
    digitalWrite(relay2,HIGH);
  }
  
  Firebase.getInt(firebaseData,"/Control/App3");
  a3 = firebaseData.intData();
  if (a3 == 0){
    Serial.println("Appliance 3 is off");
    digitalWrite(relay3,LOW);
  }else{
    Serial.println("Appliance 3 is on");
    digitalWrite(relay3,HIGH);
  }
  
  Firebase.getInt(firebaseData,"/Control/App4");
  a4 = firebaseData.intData();
  if (a4 == 0){
    Serial.println("Appliance 4 is off");
    digitalWrite(relay4,LOW);
  }else{
    Serial.println("Appliance 4 is on");
    digitalWrite(relay4,HIGH);
  }
  delay(500);
}

void intruderAlarm(){
  if(digitalRead(pirPin) == HIGH){
    noti = 1;
    Firebase.setInt(firebaseData,"/Alarm", noti);
  }else{
    noti = 0;
    Firebase.setInt(firebaseData,"/Alarm", noti);
  }
  delay(500);
}

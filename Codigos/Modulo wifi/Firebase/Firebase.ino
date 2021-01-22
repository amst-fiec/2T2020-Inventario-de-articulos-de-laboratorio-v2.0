
/**
 * Created by K. Suwatchai (Mobizt)
 * 
 * Email: k_suwatchai@hotmail.com
 * 
 * Github: https://github.com/mobizt
 * 
 * Copyright (c) 2020 mobizt
 *
*/

#include "FirebaseESP8266.h"
#include <ESP8266WiFi.h>


#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>

//1. Change the following info
#define WIFI_SSID "NETLIFE-MOREANO"
#define WIFI_PASSWORD "papatuto123"
#define FIREBASE_HOST "amst-proyecto-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "xiBBcfA1736EhFLHtHrN9VcKYMZEVlIwoEWvNgmV"

//2. Define FirebaseESP8266 data object for data sending and receiving
FirebaseData fbdo;
char input;


void setup()
{

  Serial.begin(115200);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();


  //3. Set your Firebase info

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

  //4. Enable auto reconnect the WiFi when connection lost
  Firebase.reconnectWiFi(true);
}

void loop()
{
   if (Serial.available()>0){
 
    input=Serial.read();
 
   //Serial.println(input);

    
  //5. Try to set int data to Firebase
  //The set function returns bool for the status of operation
  //fbdo requires for sending the data
  if(input=='1'){
  if(Firebase.setInt(fbdo, "/users/0/laboratorios/0/equipos/-MRbk-oRenV4_QL2iugy/estado", 1))
  {

  }else{
    //Failed?, get the error reason from fbdo

    Serial.print("Error in setInt, ");
    Serial.println(fbdo.errorReason());
  }
  }

  if(input=='0'){
      if(Firebase.setInt(fbdo, "/users/0/laboratorios/0/equipos/-MRbk-oRenV4_QL2iugy/estado", 0))
  {

  }else{
    //Failed?, get the error reason from fbdo

    Serial.print("Error in setInt, ");
    Serial.println(fbdo.errorReason());
  }
    
  }
 
  }
  }
  
  

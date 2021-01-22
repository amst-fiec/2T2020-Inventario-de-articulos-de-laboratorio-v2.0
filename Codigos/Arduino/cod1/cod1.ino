#include <SoftwareSerial.h>

SoftwareSerial esp01(2,3);

int TRIG = 10;      // pin del trigger en el puerto 10
int ECO = 9;      // pin del eco en el puerto 9
int tiempo;
int dis;
int led=8;
int ledGreen=7;
int estado;

void setup()
{
  esp01.begin(115200);
  //ENTRADAS Y SALIDAS
  pinMode(TRIG, OUTPUT);  // trigger como salida
  pinMode(ECO, INPUT);    // echo como entrada
  Serial.begin(9600);     // activar la comunicacion serial a 9600 bps
  pinMode(led,OUTPUT);
  pinMode(ledGreen,OUTPUT);
}

void loop()
{
  // CUERPO 
  digitalWrite(TRIG, HIGH);  // emision del pulso
  delay(1);       // duracion
  digitalWrite(TRIG, LOW);    // del sensor
  
  tiempo = pulseIn(ECO, HIGH);  // con funcion pulseIn se espera un pulso
  dis = tiempo / 58.2;    // ecuacion para convertir el valor de duracion en una distancia dada en cm

  if(dis>11){
    tiempo=pulseIn(ECO,HIGH);     
    digitalWrite(led, HIGH);
    digitalWrite(ledGreen, LOW);
    Serial.println('0');
    esp01.print('0');
    delay(200);
    
  }
else{
  Serial.println(dis);
  digitalWrite(led, LOW);
  digitalWrite(ledGreen, HIGH);
  Serial.println('1');
  esp01.print('1');
  delay(200);}
  delay(100);
  
}

const int boton1Pin = 12; // Pin digital al que está conectado el botón 1
const int boton2Pin = 11; // Pin digital al que está conectado el botón 2
const int boton3Pin = 10; // Pin digital al que está conectado el botón 3
const int boton4Pin = 9; // Pin digital al que está conectado el botón 4
const int boton5Pin = 8; // Pin digital al que está conectado el botón 5
const int boton6Pin = 13; // Pin digital al que está conectado el botón 5
const int buzzerPin = 7; // Pin digital al que está conectado el buzzer
const int ledRPin = 5; // Pin digital al que está conectado el LED Rojo
const int ledGPin = 3; // Pin digital al que está conectado el LED Verde
const int ledBPin = 6; // Pin digital al que está conectado el LED Azul
int ledRGBState = LOW; // Estado actual del LED RGB

void setup() {
  pinMode(boton1Pin, INPUT); // Configura el pin del botón 1 como entrada
  pinMode(boton2Pin, INPUT); // Configura el pin del botón 2 como entrada
  pinMode(boton3Pin, INPUT); // Configura el pin del botón 3 como entrada
  pinMode(boton4Pin, INPUT); // Configura el pin del botón 4 como entrada
  pinMode(boton5Pin, INPUT); // Configura el pin del botón 5 como entrada
   pinMode(boton6Pin, INPUT); // Configura el pin del botón 5 como entrada
  pinMode(buzzerPin, OUTPUT); // Configura el pin del buzzer como salida
  pinMode(ledRPin, OUTPUT); // Configura el pin del LED Rojo como salida
pinMode(ledGPin, OUTPUT); // Configura el pin del LED Verde como salida
pinMode(ledBPin, OUTPUT); // Configura el pin del LED Azul como salida

  
  Serial.begin(9600); // Inicializa la comunicación serial a 9600 baudios
}

void loop() {
  int boton1Estado = digitalRead(boton1Pin); // Lee el estado del botón 1
  int boton2Estado = digitalRead(boton2Pin); // Lee el estado del botón 2
  int boton3Estado = digitalRead(boton3Pin); // Lee el estado del botón 3
  int boton4Estado = digitalRead(boton4Pin); // Lee el estado del botón 4
  int boton5Estado = digitalRead(boton5Pin); // Lee el estado del botón 5
  int boton6Estado = digitalRead(boton6Pin); // Lee el estado del botón 5
  
  if (boton1Estado == HIGH) { // Si se presiona el botón 1
    Serial.println("1"); // Envía el número 1 a través de la consola serial
    tone(buzzerPin, 1000, 100); // Hace sonar el buzzer durante 100 milisegundos a 1000Hz
    delay(500); // Espera un momento para evitar rebotes
  }
  else if (boton2Estado == HIGH) { // Si se presiona el botón 2
    Serial.println("5"); // Envía el número 2 a través de la consola serial
    
    delay(500); // Espera un momento para evitar rebotes
  }
  else if (boton3Estado == HIGH) { // Si se presiona el botón 3
    Serial.println("3"); // Envía el número 3 a través de la consola serial
    tone(buzzerPin, 1000, 100); // Hace sonar el buzzer durante 100 milisegundos a 1000Hz
    delay(500); // Espera un momento para evitar rebotes
  }
  else if (boton4Estado == HIGH) { // Si se presiona el botón 4
    Serial.println("4"); // Envía el número 4 a través de la consola serial
    tone(buzzerPin, 1000, 100); // Hace sonar el buzzer durante 100 milisegundos a 1000Hz
    delay(500); // Espera un momento para evitar rebotes
  }
  else if (boton5Estado == HIGH) { // Si se presiona el botón 5
    Serial.println("2"); // Envía el número 5 a través de la consola serial
    tone(buzzerPin, 1000, 100); // Hace sonar el buzzer durante 100 milisegundos a 1000Hz
    delay(500); // Espera un momento para evitar rebotes
  }
   else if (boton6Estado == HIGH) { // Si se presiona el botón 5
    Serial.println("6"); // Envía el número 5 a través de la consola serial
    tone(buzzerPin, 1000, 100); // Hace sonar el buzzer durante 100 milisegundos a 1000Hz
    delay(500); // Espera un momento para evitar rebotes
  }
  if (Serial.available() > 0) { // Si hay datos disponibles en la consola serial
    int valor = Serial.read(); // Lee el valor enviado desde Java
    if (valor == '1') { // Si el valor es igual a 1
        digitalWrite(5, HIGH); // Enciende el LED rojo en el pin 3
        digitalWrite(3, LOW); // Apaga el LED verde en el pin 4
        digitalWrite(6, LOW); // Apaga el LED azul en el pin 5
        delay(200);
        digitalWrite(5, LOW); // Enciende el LED rojo en el pin 3
        digitalWrite(3, LOW); // Apaga el LED verde en el pin 4
        digitalWrite(6, LOW); // Apaga el LED azul en el pin 5
    }
    if (valor == '2') { // Si el valor es igual a 1
        digitalWrite(5, LOW); // Enciende el LED rojo en el pin 3
        digitalWrite(3, HIGH); // Apaga el LED verde en el pin 4
        digitalWrite(6, LOW); // Apaga el LED azul en el pin 5
        delay(200);
        digitalWrite(5, LOW); // Enciende el LED rojo en el pin 3
        digitalWrite(3, LOW); // Apaga el LED verde en el pin 4
        digitalWrite(6, LOW); // Apaga el LED azul en el pin 5
        
    }
     if (valor == '3') { // Si el valor es igual a 1
        digitalWrite(5, LOW); // Enciende el LED rojo en el pin 3
        digitalWrite(3, LOW); // Apaga el LED verde en el pin 4
        digitalWrite(6, HIGH); // Apaga el LED azul en el pin 5
        delay(200);
        digitalWrite(5, LOW); // Enciende el LED rojo en el pin 3
        digitalWrite(3, LOW); // Apaga el LED verde en el pin 4
        digitalWrite(6, LOW); // Apaga el LED azul en el pin 5
        
    }
}
}

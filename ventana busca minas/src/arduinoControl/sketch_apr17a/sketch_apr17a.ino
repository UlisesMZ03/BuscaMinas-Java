int buzzerPin = 7;
int espera = 50;

void setup() {
  pinMode(buzzerPin, OUTPUT);
}

void loop() {
  tone(buzzerPin, 392, 200);
  delay(espera);
  tone(buzzerPin, 392, 200);
  delay(espera);
  tone(buzzerPin, 392, 200);
  delay(espera);
  tone(buzzerPin, 330, 200);
  delay(espera);
  tone(buzzerPin, 262, 200);
  delay(espera);
  tone(buzzerPin, 247, 200);
  delay(espera);
  tone(buzzerPin, 466, 200);
  delay(espera);
  delay(500);
}

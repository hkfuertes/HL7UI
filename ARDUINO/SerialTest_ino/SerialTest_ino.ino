int cont, id_mensaje = 0;
void setup(){
  Serial.begin(9600);
}
void loop(){
  float temperatura = 37.0;
  uint8_t pulso = 70;
  uint8_t saturacion = 98;
  
  String d = date((long)millis(),2016,05,19,18,9,0);  
  // ASSYNC
  Serial.print((char)0x0b); //vt
  
  Serial.print(MSH("PC25",d, id_mensaje));
  Serial.print('\n'); 
  
  Serial.print(PID("PC25"));
  Serial.print('\n'); 
  
  Serial.print( OBR("PC25"));
  Serial.print('\n'); 
  
  Serial.print( OBX1(temperatura));
  Serial.print('\n'); 
  
  Serial.print( OBX2(pulso));
  Serial.print('\n'); 
  
  Serial.print( OBX3(saturacion));
  Serial.print('\n'); 
  
  
  Serial.print((char)0x1c); //fs
  Serial.print('\n'); 
  // Fin ASSINC
  
  id_mensaje = id_mensaje + 1;
  delay(100);
}

String padMascara(unsigned long numero, unsigned long mascara){
  if(numero < mascara){
    unsigned long resultado = numero + mascara;
    String snumero = String(resultado, DEC);
    snumero.setCharAt(0,'0');
    return snumero;
  }else{
     return String(numero, DEC);
  }
}

String date(long ms, int year, int month, int day, byte h, byte m, byte s){
  long inicial = ((long)h * 3600) + ((long)m * 60) + (long)s;
  long resto = (ms / 1000) + inicial;

  byte segs = 0, hora = 0, mi = 0;
  segs =( resto % 60)+ s;
  
  resto = resto / 60;
  mi = (resto % 60);
  resto = resto / 60;
  hora = (resto % 24);
  resto = resto / 24 + day;
  return String(String(year) + padMascara(month, 10) + padMascara(resto, 10) + padMascara( hora,10) + padMascara( mi, 10) + padMascara( segs ,10)+ "+0100");
}

String MSH (String grupo_id, String hora,  int id_mensaje){
    return "MSH|^~\\&|"+grupo_id+"ED^^||||"+hora+"||ORU^R01^ORU_R01|"+String(id_mensaje)+"|P|2.6|||AL|AL|||||"+grupo_id+"^^^";
}
String PID(String grupo_id){
   return "PID|||"+grupo_id+"^^^UPNA^^^^^^||FUERTES^MIGUEL^J^I^SR^^L~FUERTES^MIGUEL^J^I^SR^^N|";
}

String OBR(String grupo_id){
    return "OBR|1|"+grupo_id+"|"+grupo_id+"|29274-8^Vital signs measurements^LN|";
}

String OBX1(float temperatura){
   return "OBX|1|NM|8310-5^TEMPERATURA CORPORAL^LN|1|"+String(temperatura, 2)+"|Cel^GRADOS CENTIGRADO^UCUM|||||N|";
}

String OBX2(uint8_t pulso){
  return "OBX|2|NM|8867-4^PULSO CORPORAL^LN|1|"+String(pulso)+"|{beats}/min^PULSO POR MINUTO^UCUM|||||N|";
}

String OBX3(uint8_t saturacion){
  return "OBX|3|NM|20564-1^OXIMETRIA CORPORAL^LN|1|"+String(saturacion)+"|%^SATURACION EN SANGRE^UCUM|||||N|";
}

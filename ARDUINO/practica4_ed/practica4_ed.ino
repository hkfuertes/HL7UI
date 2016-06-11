#include <eHealth.h>
#include <String.h>
#include <PinChangeInt.h>
#include <PinChangeIntConfig.h>

#define NUMERO_MUESTRAS_SEGUNDO 200

char current;
int cont, id_mensaje = 0;

void setup(){
  Serial.begin(9600);
  while (!Serial); // wait for serial port to connect. Needed for native USB port only
  
  cont = 0;

 eHealth.initPulsioximeter ();
 PCintPort::attachInterrupt ( 6, readPulsioximetro, RISING );
}
void loop(){ 
  
  //Consumimos el segundo aqui.
  float* muestras = recuperaMuestras();
  // ASSYNC
  Serial.print((char)0x0b); //vt
  
  Serial.print("MSH|^~\\&|PC25ED^^||||"+date((long)millis(),2016,05,10,20,49,00));
  Serial.print("||ORU^R01^ORU_R01|"+String(id_mensaje)+"|P|2.6|||AL|AL|||||PC25^^^");
  Serial.print("\r\n"); 
  
  Serial.print("PID||PC25^^^UPNA^^^^^^|||FUERTES^MIGUEL^J^I^SR^^L~FUERTES^MIGUEL^J^I^SR^^N|");
  Serial.print("\r\n"); 
  
  Serial.print( "OBR|1|PC25|PC25|29274-8^Vital signs measurements^LN|");
  Serial.print("\r\n"); 
  
  Serial.print( "OBX|1|NM|8310-5^TEMPERATURA CORPORAL^LN|1|");
  Serial.print( eHealth.getTemperature());
  Serial.print( "|Cel^GRADOS CENTIGRADO^UCUM|||||N|");
  Serial.print("\r\n"); 
  
  Serial.print("OBX|2|NM|8867-4^PULSO CORPORAL^LN|1|");
  Serial.print(String( eHealth.getBPM ())+"|{beats}/min^PULSO POR MINUTO^UCUM|||||N|");
  Serial.print("\r\n"); 
  
  Serial.print( "OBX|3|NM|20564-1^OXIMETRIA CORPORAL^LN|1|");
  Serial.print(String( eHealth.getOxygenSaturation ())+"|%^SATURACION EN SANGRE^UCUM|||||N|");
  Serial.print("\r\n"); 

  //WCM para datos ecg a 200 muestras por segundo
  Serial.print( "OBX|4|NM|0^MDC_ATTR_SAMP_RATE^MDC |1|");
  Serial.print(NUMERO_MUESTRAS_SEGUNDO);
  Serial.print("|264608^MDC_DIM_PER_SEC");
  Serial.print("\r\n");
  
  //WCM Las muestras de ecg
  Serial.print( "OBX|5|NA|149504^ MDC_PULS_OXIM_PLETH^MDC |1|");
  for(int i = 0; i<NUMERO_MUESTRAS_SEGUNDO; i=i+1){
    Serial.print(muestras[i]);
    if(i != NUMERO_MUESTRAS_SEGUNDO -1)
      Serial.print("^");
  }
  Serial.print("1027^3504^4586^6612^8234^10592^11250^12183^11490"); //Muestras
  Serial.print("||||||||| 20080515121000.100");
  Serial.print("\r\n");

  Serial.print((char)0x1c); //fs
  Serial.print('\n'); //cr
  // Fin ASSINC
  
  id_mensaje = id_mensaje + 1;
  //delay(1000);
}

void readPulsioximetro () {
 cont++;
 if ( cont == 50 ) {
 eHealth.readPulsioximeter ();
 cont = 0;
 }
}

float* recuperaMuestras(){
  float retVal[NUMERO_MUESTRAS_SEGUNDO];
  for(int i=0; i<NUMERO_MUESTRAS_SEGUNDO; i=i+1){
   retVal[i] = eHealth.getECG ();
   delay(1000/NUMERO_MUESTRAS_SEGUNDO);
  }
  return (float*) &retVal; 
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


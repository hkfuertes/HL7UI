package Model;

public class Observacion {
	public static final String TEMPERATURA_LOINC = "8310-5";
	public static final String PULSO_LOINC = "8867-4";
	public static final String SATURACION_LOINC = "20564-1";
	public static final String EKG_DATA = "149504";
	public static final String EKG_METADATA = "0";
	
	public String tipo, unidades, medida;
	
	public String toString(){
		return medida+" "+unidades;
	}
}

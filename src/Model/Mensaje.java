package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mensaje {
	public String endevice, id_mensaje;
	public Date hora;
	private SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMddHHmmssz");
	
	public void setHora(String hora){
		try {
			this.hora = dateParser.parse(hora);
		} catch (ParseException e) {
			this.hora = new Date();
		}
	}
}

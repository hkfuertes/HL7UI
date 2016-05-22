package Model;

public class Paciente {
	public String id, nombre, apellidos, centro;
	
	public String toString(){
		return "["+centro+"] "+ "["+id+"] "+nombre + " "+apellidos;
	}
	
	@Override
	public boolean equals(Object p){
		if(p instanceof Paciente){
			Paciente q = (Paciente) p;
			return q.id.equals(id);
		}
		return false;
	}
}

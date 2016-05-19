package Model;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class HL7OBXSegment extends HL7Segment {
    String tipo, medida, unidades;

    public HL7OBXSegment(String raw_data) {
        super(raw_data);
        String[] partes = raw_data.split("\\|");
        tipo = partes[3];
        medida = partes[5];
        unidades = partes[6];
    }

    public String toString() {
        return tipo + " " + medida + " " + unidades;
    }
}

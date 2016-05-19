package Model;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class HL7OBRSegment extends HL7Segment {
    String tipo_mensaje;

    public HL7OBRSegment(String raw_data) {
        super(raw_data);
        tipo_mensaje = raw_data.split("\\|")[4];
    }

    public String toString() {
        return tipo_mensaje;
    }
}

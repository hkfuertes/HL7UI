package Model;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class HL7PIDSegment extends HL7Segment {
    String[] p_nombres;

    public HL7PIDSegment(String raw_data) {
        super(raw_data);
        p_nombres = raw_data.split("\\|")[5].split("~");
    }

    public String toString() {
        //return Arrays.toString(p_nombres);
        return p_nombres[0];
    }


}

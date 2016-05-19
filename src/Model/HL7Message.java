package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class HL7Message {
    ArrayList<HL7Segment> segments = new ArrayList<HL7Segment>();
    String divididor = "\\|"; //Por defecto
    String raw_msh;

    public void addSegment(HL7Segment segment) {
        this.segments.add(segment);
    }

    String ed, id_mensaje;
    Date hora;
    //20160519181143+0100
    SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMddHHmmssz");

    public HL7Message(String MSH) {
        raw_msh = MSH;
        //this.divididor = String.valueOf(MSH.replace(ASSYNCProtocol.START_CHAR + "MSH","").charAt(0));
        String[] partes = MSH.split(divididor);
        ed = partes[2].replace("^", "");
        id_mensaje = partes[9];

        try {
            hora = dateParser.parse(partes[6]);
        } catch (ParseException pe) {
            hora = new Date();
        }
    }

    public String toString() {
        return hora + " - " + ed + " - " + id_mensaje + "\n" + segments;
    }

    public ArrayList<HL7Segment> parse() {
        return segments;
    }

    public String rawString() {
        String retval = raw_msh + "\n";
        for (HL7Segment current : segments) {
            retval += current.getRawData() + "\n";
        }
        return retval;
    }
}

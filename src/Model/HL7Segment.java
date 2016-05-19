package Model;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class HL7Segment {
    String raw_data;

    public HL7Segment(String raw_data) {
        this.raw_data = raw_data;
    }

    public String toString() {
        return raw_data;
    }

    public String getRawData() {
        return raw_data;
    }
}

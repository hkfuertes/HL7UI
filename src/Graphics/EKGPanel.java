package Graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import Model.Mensaje;
import Model.ORUUtils;
import Model.Observacion;
import Model.Paciente;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;

public class EKGPanel extends JPanel implements ActionListener{
	
	private TimeSeries ekgSeries;
	private JToggleButton capturar;
	private JButton btnLimpiar;
	private JButton btnGuardar;
	private boolean autoclear;
	private Paciente paciente = null;
	private Observacion ekg;
	private Observacion mekg;

	public EKGPanel(){
		this(false);
	}
	
	public EKGPanel(Paciente p){
		this(true);
		this.paciente = p;
	}
	
	public EKGPanel(boolean autoclear){
		this.autoclear = autoclear;
		setLayout(new BorderLayout(0, 0));
		ekgSeries = new TimeSeries("EKG");

		ChartPanel ecg_chart = createEKGChartPanel(ekgSeries);

		JPanel ekg_panel_buttons = new JPanel(); 
		ekg_panel_buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		capturar = new JToggleButton("CAPTURAR");
		ekg_panel_buttons.add(capturar);
		
		btnLimpiar = new JButton("LIMPIAR");
		btnLimpiar.setActionCommand("clear");
		btnLimpiar.addActionListener(this);
		ekg_panel_buttons.add(btnLimpiar);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setActionCommand("save");
		ekg_panel_buttons.add(btnGuardar);
		
		add(ecg_chart, BorderLayout.CENTER);
		if(!autoclear)
			add(ekg_panel_buttons, BorderLayout.SOUTH);
	}
	
	public void printMessage(ORU_R01 message){
		if(autoclear && ekgSeries.getItemCount() > 200){
			ekgSeries.clear();
		}
		
		Mensaje msg = ORUUtils.getMensaje(message);
		ArrayList<Observacion> obsInMsg = ORUUtils.getObservaciones(message);
		
		for(Observacion obx : obsInMsg){
			if(obx.tipo.equals(Observacion.EKG_DATA)){
				ekg = obx;
			}else if(obx.tipo.equals(Observacion.EKG_METADATA)){
				mekg = obx;
			}
		}
		int millis = 1000/Integer.parseInt(mekg.medida);
		String[] medidas = ekg.medida.split("\\^");
		if(capturar.isSelected() || autoclear)
			for(int i = 0; i<medidas.length; i++){
				ekgSeries.add(new Millisecond(date_plus_ms(msg.hora,i*millis)), Double.parseDouble(medidas[i]));
			}
		//System.out.println(mekg);
	}
	
	private ChartPanel createEKGChartPanel(TimeSeries ekgSeries){
		//http://www.jfree.org/jfreechart/api/javadoc/src-html/org/jfree/chart/demo/TimeSeriesChartDemo1.html
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"ECG",  // title
				"Tiempo",             // x-axis label
				"mV",   // y-axis label
				new TimeSeriesCollection(ekgSeries),           // data
				false,               // create legend?
				false,               // generate tooltips?
				false               // generate URLs?
				);
		return new ChartPanel(chart);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ekgSeries.clear();
	}

	public Paciente getPaciente() {
		return paciente;
	}
	
	public Date date_minus_ms (Date date, int millis) {
	    
	    Calendar newDate = Calendar.getInstance();
	    newDate.setTimeInMillis(date.getTime());
	    newDate.add(Calendar.MILLISECOND, millis);
	    
	    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS M z");
	    System.out.println(dateFormatter.format(newDate.getTime()));
	    return newDate.getTime();
	    

	}
	
	public Date date_plus_ms (Date date, int millis) {
	    
	    Calendar newDate = Calendar.getInstance();
	    newDate.setTimeInMillis(date.getTime());
	    newDate.add(Calendar.MILLISECOND, millis);
	    
	    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS M z");
	    System.out.println(dateFormatter.format(newDate.getTime()));
	    return newDate.getTime();
	    

	}

}

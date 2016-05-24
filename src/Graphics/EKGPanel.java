package Graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
			if(obx.tipo.equals(Observacion.EKG_LOINC)){
				if(capturar.isSelected() || autoclear)
					ekgSeries.add(new Millisecond(msg.hora), Double.parseDouble(obx.medida));
				break;
			}
		}
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

}

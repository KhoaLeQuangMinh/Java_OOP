package core;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import de.tudresden.sumo.cmd.*;
import de.tudresden.sumo.objects.*;
import it.polito.appeal.traci.SumoTraciConnection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class TrafficLightManager {
	private SumoTraciConnection conn;
	private List<String> traffic_light_id_list = new ArrayList<>();
	
	TrafficLightManager(SumoTraciConnection conn){
		this.conn = conn;
		SumoStringList tls_idlist = new SumoStringList();
		try {
			Object result = this.conn.do_job_get(Trafficlight.getIDList());
			tls_idlist = (SumoStringList) result;
		}
		catch (Exception e) {
            alertError("SUMO Traffic Light Connection Failed", e.getMessage());
        }
		this.traffic_light_id_list.addAll(tls_idlist);
//		for(String i: tls_idlist)
//			System.out.println(i);
	}
	
	public List<String> get_traffic_light_id_list(){
		return traffic_light_id_list;
	}
	
	public List<String> get_traffic_light_controlled_junctions_list(String traffic_light_id){
		SumoStringList junction_idlist = new SumoStringList();
		try {
			Object result = this.conn.do_job_get(Trafficlight.getControlledJunctions(traffic_light_id));
			junction_idlist = (SumoStringList) result;
		}
		catch (Exception e) {
            alertError("SUMO Traffic Light Get Controlled Junctions Failed", e.getMessage());
        }
		return junction_idlist;
	}
	
	public String getCurrentLightState(String traffic_light_id) {
		String output_list = "";
		try {
			Object result = this.conn.do_job_get(Trafficlight.getRedYellowGreenState(traffic_light_id));
			output_list = (String) result;
		}
		catch (Exception e) {
            alertError("SUMO Get Current Light State Failed", e.getMessage());
        }
		return output_list;
	}
	
	public void setCurrentLightState(String traffic_light_id,String new_state) {
		try {
			this.conn.do_job_set(Trafficlight.setRedYellowGreenState(traffic_light_id, new_state));
		}
		catch (Exception e) {
            alertError("SUMO Set Current Light State Failed", e.getMessage());
        }
		return;
	}
	
	public double getCurrentPhaseDuration(String traffic_light_id) {
		double output = 0;
		try {
			Object result = this.conn.do_job_get(Trafficlight.getPhaseDuration(traffic_light_id));
			output = (double) result;
		}
		catch (Exception e) {
            alertError("SUMO Get Current Light Phase Duration Failed", e.getMessage());
        }
		return output;
	}
	
	public void setCurrentPhaseDuration(String traffic_light_id, double new_phase_duration) {
		try {
			this.conn.do_job_set(Trafficlight.setPhaseDuration(traffic_light_id, new_phase_duration));
		}
		catch (Exception e) {
            alertError("SUMO Set Current Light Phase Duration Failed", e.getMessage());
        }
	}
	
	public double getNextSwitch(String traffic_light_id) {
		double output = 0;
		try {
			Object result = this.conn.do_job_get(Trafficlight.getNextSwitch(traffic_light_id));
			output = (double) result;
		}
		catch (Exception e) {
            alertError("SUMO Get Current Light State Failed", e.getMessage());
        }
		return output;
	}
	
	private void alertError(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, msg);
            a.setTitle(title); a.show();
        });
    }
}

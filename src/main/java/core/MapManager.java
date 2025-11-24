package core;

import de.tudresden.sumo.cmd.Edge;
import de.tudresden.sumo.cmd.Vehicle;
import it.polito.appeal.traci.*;

import java.io.IOException;
import java.util.*;

public class MapManager {
	private SumoTraciConnection conn;
	public MapManager(SumoTraciConnection conn) {
		this.conn = conn;
	}
	
	public Map<String, Map<String, String>> getEdges() throws Exception{
		Map<String, Map<String, String>> edges = new HashMap<>();
		List<String> edgeIDs = (List<String>) conn.do_job_get(Edge.getIDList());
		for(int i = 0; i < edgeIDs.size(); i++) {
			String edgeID = edgeIDs.get(i);
			Map<String, String> edgeInfos = new HashMap<>();
			
			edgeInfos.put("CO2Emission", String.valueOf(conn.do_job_get(Edge.getCO2Emission(edgeID))));
			edgeInfos.put("COEmission", String.valueOf(conn.do_job_get(Edge.getCOEmission(edgeID))));
			edgeInfos.put("electricityConsumption", String.valueOf(conn.do_job_get(Edge.getElectricityConsumption(edgeID))));
			edgeInfos.put("fuelConsumption", String.valueOf(conn.do_job_get(Edge.getFuelConsumption(edgeID))));
			edgeInfos.put("HCEmission", String.valueOf(conn.do_job_get(Edge.getHCEmission(edgeID))));
			edgeInfos.put("laneNumber", String.valueOf(conn.do_job_get(Edge.getLaneNumber(edgeID))));
			edgeInfos.put("lastStepHaltingNumber", String.valueOf(conn.do_job_get(Edge.getLastStepHaltingNumber(edgeID))));
			edgeInfos.put("lastStepLength", String.valueOf(conn.do_job_get(Edge.getLastStepLength(edgeID))));
			edgeInfos.put("lastStepMeanSpeed", String.valueOf(conn.do_job_get(Edge.getLastStepMeanSpeed(edgeID))));
			edgeInfos.put("lastStepOccupancy", String.valueOf(conn.do_job_get(Edge.getLastStepOccupancy(edgeID))));
			edgeInfos.put("lastStepVehicleNumber", String.valueOf(conn.do_job_get(Edge.getLastStepVehicleNumber(edgeID))));
			edgeInfos.put("NOxEmission", String.valueOf(conn.do_job_get(Edge.getNOxEmission(edgeID))));
			edgeInfos.put("NoiseEmission", String.valueOf(conn.do_job_get(Edge.getNoiseEmission(edgeID))));
			edgeInfos.put("PMxEmission", String.valueOf(conn.do_job_get(Edge.getPMxEmission(edgeID))));
			edgeInfos.put("travelTime", String.valueOf(conn.do_job_get(Edge.getTraveltime(edgeID))));
			edgeInfos.put("waitingTime", String.valueOf(conn.do_job_get(Edge.getWaitingTime(edgeID))));
			
			edges.put(edgeID, edgeInfos);
		}
		
		return edges;
	}
}

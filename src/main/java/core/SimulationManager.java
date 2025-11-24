package core;

import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Route;
import de.tudresden.sumo.cmd.Vehicle;
import it.polito.appeal.traci.*;
import de.tudresden.sumo.objects.*;
import java.io.IOException;
import java.util.*;


public class SimulationManager {
	private static final String sumoPath = "/Users/khoale/sumo/bin/sumo";
	private static final String mapPath = "/Users/khoale/Downloads/SUMO_Test/SUMO_PROJECT/traffic-simulator/src/main/resources/frauasmap.sumocfg";
	
	protected SumoTraciConnection conn;
	private	Map<String, Map<String, String>> listOfEdges;
	private	Map<String, Map<String, Object>> listOfVehicles;
	private List<String> listOfTrafficLightIDs;
	private MapManager managerMap;
	private VehicleManager managerVehicle;
	private TrafficLightManager managerTrafficLight;
	private SimulationQueue queue;
	private static int routeCounter = 0;
	public boolean isRunning = false;
	
	
	public SimulationManager(SimulationQueue queue) {
		this.conn = new SumoTraciConnection(sumoPath, mapPath);
		this.managerMap = new MapManager(conn);
		this.managerVehicle = new VehicleManager(conn);
		this.managerTrafficLight = new TrafficLightManager(conn);
		this.queue = queue;
	};
	
	public void startConnection() throws Exception{
		conn.runServer();
		isRunning = true;
	}
	
	public void stopConnection() {
		isRunning = false;
		if(conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
	
	public void doTimeStep() throws Exception{
		conn.do_timestep();
		listOfEdges = (Map<String, Map<String, String>>) managerMap.getEdges();
		managerVehicle.step();
		listOfVehicles = managerVehicle.getVehiclesData();
		listOfTrafficLightIDs = managerTrafficLight.get_traffic_light_id_list();
		
		SimulationState state = new SimulationState(listOfEdges, listOfVehicles, listOfTrafficLightIDs);
		queue.putState(state);
	}	
	
	public void InjectVehicle(String vehicleId, String vehType, int r, int g, int b, int a, double Speed, String firstEdge, String lastEdge) {
		try {
			String routeID = "routes_" + routeCounter++;			
			SumoStringList edges = getRouteFromEdges(firstEdge, lastEdge, vehType).edges;
			conn.do_job_set(Route.add(routeID, edges));
			managerVehicle.injectVehicle(vehicleId, vehType, routeID, r, g, b, a, Speed);
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	public Map<String, Map<String, String>> getListOfEdges() {
		return listOfEdges;
	};
	
	public Map<String, Map<String, Object>> getListOfVehicles() {
		return listOfVehicles;
	};
	
	public SumoStage getRouteFromEdges(String firstEdge, String lastEdge, String vehType) throws Exception {
		double depart = 0;
//		int routingMode = (int) conn.do_job_get(Vehicle.getRoutingMode(vehID));
		int routingMode = 0;
//		String vehType = (String) conn.do_job_get(Vehicle.getTypeID(vehID));
		return (SumoStage) conn.do_job_get(Simulation.findRoute(firstEdge, lastEdge, vehType, depart, routingMode));
	}
}

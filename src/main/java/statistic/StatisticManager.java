package statistic;
import core.*;


import java.util.concurrent.atomic.*;
import java.util.*;

public class StatisticManager {
	private SimulationQueue queue;
	
	public StatisticManager(SimulationQueue queue) {
		this.queue = queue;
	}
	public void calculate() throws Exception {
		SimulationState state = queue.takeState();
		Map<String, Map<String, String>> snapshotEdge = state.getEdges();
		Map<String, Map<String, Object>> snapshotVehicle = state.getVehicles();
;		if(snapshotEdge == null || snapshotVehicle == null) return;
		for (String edgeID : snapshotEdge.keySet()) {
		    System.out.println(edgeID);
		    break;
		}
		if (snapshotVehicle.containsKey("5003")) {
		    System.out.println("CAR 5003 IS HERE RUNNING IN STATISTIC");
		} else {
		    System.out.println("NOT YET INJECTED");
		}
		
	}
	
}

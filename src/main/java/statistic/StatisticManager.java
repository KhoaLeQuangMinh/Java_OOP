package statistic;
import core.SharedState;


import java.util.concurrent.atomic.*;
import java.util.*;

public class StatisticManager {
	private SharedState lastState;
	
	public StatisticManager(SharedState state) {
		this.lastState = state;
	}
	public void calculate() {
		Map<String, Map<String, String>> snapshotEdge = lastState.lastState.get();
		Map<String, Map<String, Object>> snapshotVehicle = lastState.lastVehicles.get();
		if(snapshotEdge == null || snapshotVehicle == null) return;
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

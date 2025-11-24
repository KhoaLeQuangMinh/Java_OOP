package core;

import java.util.concurrent.*;
import statistic.StatisticManager;
import java.util.*;
import de.tudresden.sumo.objects.*;
import java.util.concurrent.atomic.*;


public class Controller {
	public static void main(String[] args) {
		SharedState state = new SharedState();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		SimulationManager managerSimulation = new SimulationManager(state);
		StatisticManager managerStatistic = new StatisticManager(state);
		AtomicBoolean injected = new AtomicBoolean(false);
		executor.submit(() -> {
			try {
				managerSimulation.startConnection();
				while(managerSimulation.isRunning) {
					System.out.println("THREAD 1 IS RUNNING");
					if(!injected.get()) {
						managerSimulation.InjectVehicle(String.valueOf(5003), "DEFAULT_VEHTYPE", 255, 255, 255, 0, 3.6, "66993637#0", "265499402#5");
						managerSimulation.InjectVehicle(String.valueOf(5004), "DEFAULT_VEHTYPE", 255, 255, 255, 0, 3.6, "66993637#0", "265499402#5");
						managerSimulation.InjectVehicle(String.valueOf(5005), "DEFAULT_VEHTYPE", 255, 255, 255, 0, 3.6, "66993637#0", "265499402#5");
						managerSimulation.InjectVehicle(String.valueOf(5006), "DEFAULT_VEHTYPE", 255, 255, 255, 0, 3.6, "66993637#0", "265499402#5");
						injected.set(true);
					}
					managerSimulation.doTimeStep();
					System.out.println("THREAD 1 IS SLEEPING");
					Thread.sleep(5000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				managerSimulation.stopConnection();
			}
			
		});
		executor.submit(() -> {
			while (true) {
	            System.out.println("THREAD 2 IS RUNNING");
                managerStatistic.calculate();
                try { 
        			System.out.println("THREAD 2 IS SLEEPING");
                	Thread.sleep(5000); 
                } 
                catch (InterruptedException e) {
                	break; 
                }
            }		
		});
		executor.shutdown();
	}
}

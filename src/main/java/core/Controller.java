package core;

import java.util.concurrent.*;
import statistic.StatisticManager;
import java.util.*;
import de.tudresden.sumo.objects.*;
import java.util.concurrent.atomic.*;


public class Controller {
	public static void main(String[] args) {
		SimulationQueue queue = new SimulationQueue(10);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		SimulationManager managerSimulation = new SimulationManager(queue);
		StatisticManager managerStatistic = new StatisticManager(queue);
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
                try { 
                	managerStatistic.calculate();
        			System.out.println("THREAD 2 IS SLEEPING");
                	Thread.sleep(5000); 
                } 
                catch (Exception e) {
                	break; 
                }
            }		
		});
		executor.shutdown();
	}
}

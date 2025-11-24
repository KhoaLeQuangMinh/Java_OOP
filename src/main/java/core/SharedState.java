package core;

import java.util.concurrent.atomic.*;
import java.util.*;


public class SharedState {
	public AtomicReference<Map<String, Map<String, String>>> lastState = new AtomicReference<>();
	 public AtomicReference<Map<String, Map<String, Object>>> lastVehicles = new AtomicReference<>();
	
}

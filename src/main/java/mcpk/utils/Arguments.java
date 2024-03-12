package mcpk.utils;

import java.util.HashMap;

public class Arguments extends HashMap<String,Object>{

	public Arguments() {
		this.put("duration", 1);
		this.put("airborne", false);
		this.put("forward", 0);
		this.put("strafing", 0);
		this.put("sprinting", false);
		this.put("sneaking", false);
		this.put("jumping", false);
		this.put("facing", 0f);
		
		this.put("blocking", 0.0); //sword block
		this.put("slip", 0.6D); //0.8D for ice, 0.98D for ice
		this.put("swiftness", 0.0);
		this.put("slowness", 0.0);
		
		//TODO
		this.put("soulsand", 0.0);
		
	}

}
package mcpk.functions.walk;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

public class FunctionWalkAir extends Function {

	@Override
	public String[] names() {
		return new String[] {"wa", "walkair"};
	}

	@Override
	public void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) (facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("airborne", true);
		player.move(args);
		
	}

	
	
}

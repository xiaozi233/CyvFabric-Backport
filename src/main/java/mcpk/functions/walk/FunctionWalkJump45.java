package mcpk.functions.walk;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

public class FunctionWalkJump45 extends Function {

	@Override
	public String[] names() {
		return new String[] {"wj45", "walkjump45"};
	}

	@Override
	public void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", facing + 45);
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("strafing", 1);
		player.move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		player.move(args);
		
	}

	
	
}

package mcpk.functions.nonmovement.velocity;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.nonmovement.SpecialFunction;

public class FunctionVx extends SpecialFunction {

	@Override
	public String[] names() {
		return new String[] {"vx", "setvx", "setvelocityx", "speedx", "setspeedx"};
	}

	@Override
	public void specialRun(Player player, double args, Parser parser) {
		player.vx = args;
		
	}

}

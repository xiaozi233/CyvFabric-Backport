package mcpk.functions.nonmovement.velocity;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.nonmovement.SpecialFunction;

public class FunctionVz extends SpecialFunction {

	@Override
	public String[] names() {
		return new String[] {"vz", "setvz", "setvelocityz", "speedz", "setspeedz"};
	}

	@Override
	public void specialRun(Player player, double args, Parser parser) {
		player.vz = args;
		
	}

}

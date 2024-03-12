package mcpk.functions.nonmovement.position;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.nonmovement.SpecialFunction;

public class FunctionPosX extends SpecialFunction {
	@Override
	public String[] names() {
		return new String[] {"positionx", "posx", "xposition", "xpos", "xcoord", "xcoords", "coordx", "coordsx"};
	}

	@Override
	public void specialRun(Player player, double args, Parser parser) {
		player.x = args;
		
	}
}

package mcpk.functions.nonmovement.position;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.nonmovement.SpecialFunction;

public class FunctionPosZ extends SpecialFunction {
	@Override
	public String[] names() {
		return new String[] {"positionz", "posz", "zposition", "zpos", "zcoord", "zcoords", "coordz", "coordsz"};
	}

	@Override
	public void specialRun(Player player, double args, Parser parser) {
		player.z = args;
		
	}
}

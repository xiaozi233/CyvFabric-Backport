package mcpk.functions.nonmovement.position;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.nonmovement.SpecialFunction;

public class FunctionFacing extends SpecialFunction {
	@Override
	public String[] names() {
		return new String[] {"f", "facing", "face"};
	}

	@Override
	public void specialRun(Player player, double args, Parser parser) {
		parser.default_facing = (float) args;
		
	}
}

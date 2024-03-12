package mcpk.functions.nonmovement.globals;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.nonmovement.SpecialFunction;
import mcpk.utils.ParserException;

public class FunctionSlip extends SpecialFunction {
	@Override
	public String[] names() {
		return new String[] {"slip", "slipperiness"};
	}

	@Override
	public void specialRun(Player player, double args, Parser parser) throws ParserException {
		
		parser.default_effects.put("slip", args);
		
		
	}
}

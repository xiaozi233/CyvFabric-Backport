package mcpk.functions.nonmovement.globals;

import mcpk.Parser;
import mcpk.Player;
import mcpk.functions.nonmovement.SpecialFunction;
import mcpk.utils.ParserException;

public class FunctionSwiftness extends SpecialFunction {
	@Override
	public String[] names() {
		return new String[] {"swiftness", "swift"};
	}

	@Override
	public void specialRun(Player player, double args, Parser parser) throws ParserException {
		if ((int) args != args) throw new ParserException("Potion effect level must be an integer.");
		
		parser.default_effects.put("swiftness", args);
		
		
	}
}

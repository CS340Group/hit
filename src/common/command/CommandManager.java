/**
 * 
 */
package common.command;

import java.util.Stack;

import common.Result;

/**
 * The CommandManager accepts a subclass of the AbstractCommand class, executes it, and keeps a copy of it
 * for easy undoing and redoing.
 * 
 * @author murphyra
 *
 */
public class CommandManager {
	
	private Stack<AbstractCommand> _executedCommands;
	private Stack<AbstractCommand> _undoneCommands;
	
	/**
	 * Executes a command and keeps track of it for later undo / redo.
	 * @param c
	 * @return a false Result if the command could not be executed. Or a true Result if the command could be executed.
	 */
	public Result executeCommand(AbstractCommand c) {
		if (!c.canExecute())
			return new Result(false, "Command could not be executed.");
		_executedCommands.push(c);
		return c.execute();
	}
	
	/**
	 * Undoes the last command added with executeCommand.
	 * @return a false Result if the there are no more commands to undo.
	 */
	public Result undo() {
		if (!this.canUndo())
			return new Result(false, "There are no more actions to undo.");
		
		_undoneCommands.push(_executedCommands.pop());
		return _undoneCommands.peek().undo();
	}
	
	/**
	 * Redoes the last undone command. 
	 * @return a flase Result of there are no more commands to redo.
	 */
	public Result redo() {
		if (!this.canRedo())
			return new Result(false, "There are no more actions to redo.");
		
		_executedCommands.push(_undoneCommands.pop());
		return _executedCommands.peek().execute();
	}

	private boolean canUndo() {
		return _executedCommands.isEmpty() ? false : true;
	}
	
	private boolean canRedo() {
		return _undoneCommands.isEmpty() ? false : true;
	}

}
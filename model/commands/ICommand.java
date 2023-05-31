package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * A represents command objects.
 */
public interface ICommand {
  /**
   * applies the given function to the model.
   * @param m the given model.
   */
  void applyCommand(IModel m);
}

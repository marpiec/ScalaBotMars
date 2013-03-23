package impl.command.debug

import impl.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class Log(text: String) extends Command {
  override def toString = "Log(text=" + text + ")"
}

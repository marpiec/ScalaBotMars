package impl.command.debug

import impl.data.XY
import impl.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class MarkCell(position: XY, color: String) extends Command {
  override def toString = "MarkCell(position=" + position.toString + ",color=" + color + ")"

}

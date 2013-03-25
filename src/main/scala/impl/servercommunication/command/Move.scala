package impl.servercommunication.command

import impl.data.XY

/**
 * 
 */
class Move(val direction:XY) extends Command {
  override def toString = {
    "Move(direction="+direction.toString+")"
  }
}

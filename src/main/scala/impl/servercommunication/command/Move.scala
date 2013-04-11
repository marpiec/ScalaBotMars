package impl.servercommunication.command

import impl.data.{Step, XY}

/**
 *
 */
class Move(val step: Step) extends Command {
  override def toString = {
    "Move(direction=" + step.xy.toString + ")"
  }
}

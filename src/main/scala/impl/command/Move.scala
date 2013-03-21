package impl.command

import impl.data.XY

/**
 * 
 */
class Move(val direction:XY) {
  override def toString = {
    "Move(direction="+direction.toString+")"
  }
}

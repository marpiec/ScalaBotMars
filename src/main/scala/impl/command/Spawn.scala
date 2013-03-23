package impl.command

import impl.data.XY

/**
 *
 */
class Spawn(val direction: XY, val name: String, val energy: Int, val state: Map[String, String]) extends Command {
  override def toString: String = {
    val sb = new StringBuilder("Spawn(direction=").append(direction.toString).
                        append(",name=").append(name).
                        append(",energy=").append(energy.toString)

    for ((key, value) <- state) {
      sb.append(",").append(key).append("=").append(value)
    }
    sb.append(")")
    sb.toString
  }
}

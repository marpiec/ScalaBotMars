package impl.command

import impl.data.XY

/**
 *
 */
class Spawn(direction: XY, name: String, energy: Int, val state: Map[String, String]) {
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

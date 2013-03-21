package impl.command

import impl.data.XY

object DebugCommand {

  def say(text: String) = "Say(text=" + text + ")"

  def status(text: String) = "Status(text=" + text + ")"

  def log(text: String) = "Log(text=" + text + ")"

  def markCell(position: XY, color: String) = "MarkCell(position=" + position.toString + ",color=" + color + ")"

  def drawLine(from: XY, to: XY, color: String) = "DrawLine(from=" + from.toString + ",to=" + to.toString + ",color=" + color + ")"

}

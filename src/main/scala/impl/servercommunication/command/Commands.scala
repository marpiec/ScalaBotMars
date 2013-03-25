package impl.servercommunication.command

/**
 * @author Marcin Pieciukiewicz
 */
class Commands(val commands: List[Command]) {

  def this(command:Command) = this(List(command))

  def ::(command: Command):Commands = {
    new Commands(command :: commands)
  }

  def :::(otherCommands: Commands):Commands = {
    new Commands(otherCommands.commands ::: commands)
  }

  override def toString: String = {
    val sb = new StringBuilder()
    var nonFirst = false
    for (command <- commands) {
      if (nonFirst) {
        sb.append("|")
      } else {
        nonFirst = true
      }
      sb.append(command)
    }
    sb.toString()
  }
}

package impl.servercommunication.command

/**
 * @author Marcin Pieciukiewicz
 */
class Commands {


  var commands = List[Command]()

  def addCommand(command: Command) {
    commands ::= command
  }

  def addMultipleCommands(multipleCommands: List[Command]) {
    commands :::= multipleCommands
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

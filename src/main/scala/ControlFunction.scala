import impl.servercommunication.command.SetCommand
import impl.servercommunication.function.{WelcomeFunction, ReactFunction}
import impl.reactor.{MiniBotReactHandler, BotReactHandler}
import impl.servercommunication.{CustomStatus, InputParser}

class ControlFunction {
  def respond(input: String): String = {

    try {
      //println(input)
      val response = tryToRespond(input)

      //Thread.sleep(250)

      //println(response)

      response.toString
    } catch {
      case e: Throwable => {
        e.printStackTrace()
        ""
      }
    }

  }


  def tryToRespond(input: String) = {
    val parser = new InputParser(input)

    if (parser.isWelcomeFunction) {
      val welcomeFunction = parser.result.asInstanceOf[WelcomeFunction]
      welcomeFunction.maxSlaves
      new SetCommand(Map(CustomStatus.MAX_SLAVES -> welcomeFunction.maxSlaves.toString))
    }
    if (parser.isReactFunction) {
      val reactFunction = parser.result.asInstanceOf[ReactFunction]
      if (reactFunction.generation == 0) {
        new BotReactHandler(reactFunction).respond()
      } else {
        new MiniBotReactHandler(reactFunction).respond()
      }
    } else {
      ""
    }
  }


}

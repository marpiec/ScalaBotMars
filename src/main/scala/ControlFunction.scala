import impl.languageutil.Logger
import impl.servercommunication.command.SetCommand
import impl.servercommunication.function.{WelcomeFunction, ReactFunction}
import impl.reactor.{MiniBotReactHandler, BotReactHandler}
import impl.servercommunication.{CustomStatus, InputParser}

class ControlFunction {
  def respond(input: String): String = {

    try {
      //println(input)
      val parser = new InputParser(input)
      val response = tryToRespond(parser)

      //Thread.sleep(250)

//      if (parser.isReactFunction && parser.result.asInstanceOf[ReactFunction].generation == 0) {
//        Logger.enable()
//        Logger.log(response.toString)
//        Logger.disable()
//      }

      response.toString
    } catch {
      case e: Throwable => {
        e.printStackTrace()
        ""
      }
    }

  }


  def tryToRespond(parser: InputParser) = {

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

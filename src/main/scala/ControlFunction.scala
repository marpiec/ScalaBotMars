import impl.function.ReactFunction
import impl.io.InputParser
import impl.reactor.{MiniBotReactHandler, BotReactHandler}

class ControlFunction {
  def respond(input: String): String = {

    try {
      val response = tryToRespond(input)

      //Thread.sleep(150)

      println(response)

      response.toString
    } catch {
      case e => {
        e.printStackTrace()
        ""
      }
    }

  }


  def tryToRespond(input: String) = {
    val parser = new InputParser(input)

    if (parser.isReactFunction) {
      val reactFunction = parser.result.asInstanceOf[ReactFunction]
      if(reactFunction.generation == 0) {
        new BotReactHandler(reactFunction).respond()
      } else {
        new MiniBotReactHandler(reactFunction).respond()
      }
    } else {
      ""
    }
  }


}

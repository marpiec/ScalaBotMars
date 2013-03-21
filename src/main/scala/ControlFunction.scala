import impl.function.ReactFunction
import impl.io.{OutputWriter, InputParser}
import impl.ReactHandler

class ControlFunction {
  def respond(input: String): String = {

    try {
      tryToRespond(input)
    } catch {
      case e => {
        e.printStackTrace()
        ""
      }
    }

  }


  def tryToRespond(input: String): String = {
    val parser = new InputParser(input)

    if (parser.isReactFunction) {
      ReactHandler.respond(parser.result.asInstanceOf[ReactFunction])
    } else {
      ""
    }
  }


}

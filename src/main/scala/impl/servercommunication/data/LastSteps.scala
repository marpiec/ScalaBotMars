package impl.servercommunication.data

import impl.data.XY

/**
 * 
 */
class LastSteps(val steps: List[XY]) {

  def ::(step: XY) = {
    var newSteps = step :: steps
    if (newSteps.size > LastSteps.MAX_SIZE) {
      newSteps = newSteps.init
    }
     new LastSteps(newSteps)
  }


  override def toString: String = {
    val sb = new StringBuilder
    var notFirst = false
    steps.foreach(xy => {
      if(notFirst) {
        sb.append(';')
      } else {
        notFirst = true
      }
      sb.append(xy)
    })
    sb.toString
  }
}

object LastSteps {

  val MAX_SIZE = 20

  def parse(stepsString:String) = {
    val steps = stepsString.split(';').map(XY.parse(_)).toList
    new LastSteps(steps)
  }
}

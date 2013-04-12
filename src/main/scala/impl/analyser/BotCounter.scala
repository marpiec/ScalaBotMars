
package impl.analyser

/**
 * 
 */

object BotCounter {

  val LIMIT = 100

  var currentRound = 0
  var botsInPreviousRound = 0
  var botsRegisteredInCurrentRound = 0
  var botsCreatedInCurrentRound = 0

  def registerBot(round:Int):Int = {
    this.synchronized {
       if(round==currentRound) {
         botsRegisteredInCurrentRound += 1
         botsInPreviousRound + botsCreatedInCurrentRound
       } else {
         botsInPreviousRound = botsRegisteredInCurrentRound + botsCreatedInCurrentRound
         botsRegisteredInCurrentRound = 1
         botsCreatedInCurrentRound = 0
         currentRound = round
         botsInPreviousRound
       }
    }
  }

  def createBot(round:Int):Boolean = {
    createBot(round, 0)
  }

  def createBot(round:Int, buffer:Int):Boolean = {
    this.synchronized {
      if(botsInPreviousRound + botsCreatedInCurrentRound < LIMIT - buffer) {
        botsCreatedInCurrentRound += 1
        true
      } else {
        false
      }
    }
  }


}

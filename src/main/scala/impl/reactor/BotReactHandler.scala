package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.servercommunication.command.{Commands, Move}
import impl.data.{DirectionPreferences, EntitiesTypes, XY}
import impl.analyser._
import impl.servercommunication.command.debug.{Say, DrawLine}
import senses.{MissileDefence, Hunger, Fear, CabinFever}

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)

  def respond() = {


    //println(reactFunction.viewToFormattedString())

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Hunger(viewAnalyser).getPreferences() * 1.0
    multiplePreferences ::= new Fear(viewAnalyser).getPreferences() * 1.0
    multiplePreferences ::= new CabinFever(viewAnalyser).getPreferences() * 1.0


    val missileDefence: MissileDefence = new MissileDefence(viewAnalyser)
    val antiMissileCommands = missileDefence.getCommands()


    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)


    val step: XY = DirectionAdvisor.findBestMoveFormPreferences(preferences, viewAnalyser)

    val commands = new Commands()
    commands.addCommand(new Move(step))
    commands.addMultipleCommands(antiMissileCommands)
    commands

  }

}

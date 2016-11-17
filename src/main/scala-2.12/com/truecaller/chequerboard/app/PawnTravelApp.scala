package com.truecaller.chequerboard.app

import com.truecaller.chequerboard.domain.Chequerboard.Path
import com.truecaller.chequerboard.domain._

object PawnTravelApp extends App {

  val solution: Option[Path] = WandorfsHeuristicSolver.findTravelSolution(Chequerboard(10), Position(2, 3), Pawn)

  solution match {
    case Some(s) => println(s)
    case _ => println("Solution not found")
  }

}

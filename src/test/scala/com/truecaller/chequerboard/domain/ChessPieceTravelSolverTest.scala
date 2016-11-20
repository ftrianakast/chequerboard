package com.truecaller.chequerboard.domain

import com.truecaller.chequerboard.domain.Chequerboard._
import org.scalatest.{FlatSpec, Matchers, OptionValues}


class ChessPieceTravelSolverTest extends FlatSpec with Matchers with OptionValues {

  "Chess piece travel" should "have a correct solution using Wandorf's Heuristic for a pawn in a board of size 5 and a fixed start position" in {
    val fiveBoardSolution =
      Vector(Position(2, 3), Position(2, 0),
        Position(4, 2), Position(2, 4),
        Position(0, 2), Position(3, 2),
        Position(1, 4), Position(1, 1),
        Position(4, 1), Position(4, 4),
        Position(2, 2), Position(0, 0),
        Position(3, 0), Position(3, 3),
        Position(0, 3), Position(2, 1),
        Position(4, 3), Position(4, 0),
        Position(1, 0), Position(1, 3),
        Position(3, 1), Position(0, 1),
        Position(0, 4), Position(3, 4),
        Position(1, 2))
    val solution: Path = testCorrectSolutionsForAPawnTravel(5, Position(2, 3))
    solution should be(fiveBoardSolution)
  }

  "Chess piece travel" should "have a correct solution using Wandorf's Heuristic for a pawn in a board of size 10 and a fixed start position" in {
    testCorrectSolutionsForAPawnTravel(10, Position(2, 3))
  }

  def testCorrectSolutionsForAPawnTravel(size: Int, startPosition: Position): Path = {
    val completeCoordinates: Set[Position] = (for (x <- 0 to size; y <- 0 to size) yield Position(x, y)).toSet
    val solution: Option[Path] = WandorfsHeuristicSolver.findTravelSolution(Chequerboard(size), startPosition, Pawn)
    testExistenceProperty(solution)
    testCompletenessProperty(solution.get, completeCoordinates, size)
    testCorrectMovementsProperty(solution.get, Pawn)
    solution.get
  }

  def testExistenceProperty(solution: Option[Path]): Path = {
    solution should not be None
    solution.get
  }

  def testCompletenessProperty(solution: Path, completeCoordinates: Set[Position], size: Int): Path = {
    solution.size should be(size * size)
    solution.distinct.size should be(solution.size)
    solution.toSet diff completeCoordinates should be(Set.empty)
    solution
  }

  def testCorrectMovementsProperty(solution: Path, chessPiece: ChessPiece): Path = {
    def verifyStepsCorrectness(step: Position, nextStep: Position): Boolean = {
      val delta = Position(nextStep.x - step.x, nextStep.y - step.y)
      chessPiece.getDeltas contains delta
    }
    val adjacentSteps = solution.sliding(2)
    val movementsCorrectness = adjacentSteps.forall(pairPosition => verifyStepsCorrectness(pairPosition.head, pairPosition(1)))
    movementsCorrectness should be(true)
    solution
  }

}

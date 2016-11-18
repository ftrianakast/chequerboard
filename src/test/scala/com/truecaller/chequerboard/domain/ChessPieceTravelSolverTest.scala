package com.truecaller.chequerboard.domain

import com.truecaller.chequerboard.domain.Chequerboard._
import org.scalacheck.Gen
import org.scalatest.{FlatSpec, Matchers, OptionValues}


class ChessPieceTravelSolverTest extends FlatSpec with Matchers with OptionValues {


  "Chess piece travel" should "have a correct solution using Wandorf's Heuristic for a pawn in a board of size 5 and a fixed start position" in {
    val fiveBoardSolution =
      Vector(
        Position(2, 3), Position(2, 0),
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
    testCorrectSolutionsForAPawnTravel(Some(fiveBoardSolution), 5, Position(2, 3))
  }

  "Chess piece travel" should "have a correct solution using Wandorf's Heuristic for a pawn in a board of size 10 and a fixed start position" in {
    testCorrectSolutionsForAPawnTravel(None, 10, Position(2, 3))
  }

  "Chess piece travel" should "have a correct solution using Wandorf's Heuristic for a pawn in a board of random size and a random start position" in {
    val gen: Gen[(Chequerboard, Position)] = {
      for {
        size <- Gen.choose(5, 20)
        x <- Gen.choose(0, size)
        y <- Gen.choose(0, size)
      } yield (Chequerboard(size), Position(x, y))
    }
    val example = gen.sample.getOrElse((Chequerboard(10), Position(2, 3)))
    testCorrectSolutionsForAPawnTravel(None, example._1.size, example._2)
  }

  def testCorrectSolutionsForAPawnTravel(expectedSolution: Option[Path], size: Int, startPosition: Position) = {
    val solution: Option[Path] = WandorfsHeuristicSolver.findTravelSolution(Chequerboard(size), startPosition, Pawn)
    solution should not be None
    solution.get.size should be(size * size)
    solution.get.distinct.size should be(solution.get.size)
    expectedSolution match {
      case Some(s) =>
        solution.get should be(s)
      case _ =>
    }
  }

}

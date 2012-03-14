package edu.washington.cs.knowitall
package common
package enrich

import collection.immutable.Bag

import org.junit.runner.RunWith
import org.specs.runner.JUnit4
import org.specs.Specification
import org.specs.runner.JUnitSuiteRunner

@RunWith(classOf[JUnitSuiteRunner])
class TraversableSpecTest extends JUnit4(TraversableSpec)
object TraversableSpec extends Specification {
  import Traversables._

  "simple histogram works fine" in {
    val h1 = List(1, 2, 2, 3, 3, 3).histogram
    val h2 = List(3, 2, 1, 3, 2, 3).histogram
    h1 must_== h2
    h1 must haveTheSameElementsAs(List((1, 1), (2, 2), (3, 3)))
  }

  "histogram from partials works fine" in {
    val h1 = List((1, 1), (2, 2), (2, 2), (3, 3), (3, 3), (3, 3)).mergeHistograms
    val h2 = List((1, 1), (2, 2), (2, 2), (3, 3), (3, 3), (3, 3)).reverse.mergeHistograms
    h1 must_== h2
    h1 must haveTheSameElementsAs(List((1, 1), (2, 4), (3, 9)))
  }

  "list multimaps works fine" in {
    val list = List(1 -> 1, 1 -> 2, 1 -> 1, 2 -> 2)
    val multimap = list.toListMultimap

    multimap must haveTheSameElementsAs(Map(1 -> List(1, 2, 1), 2 -> List(2)))

    val extended = (multimap.toSeq :+ (1 -> List(2, 3, 4, 5)))
    val merged = extended.mergeListMultimaps

    merged must haveTheSameElementsAs(Map(1 -> List(1, 2, 1, 2, 3, 4, 5), 2 -> List(2)))
  }

  "set multimaps works fine" in {
    val list = List(1 -> 1, 1 -> 2, 1 -> 1, 2 -> 2)
    val multimap = list.toSetMultimap

    multimap must haveTheSameElementsAs(Map(1 -> Set(1, 2), 2 -> Set(2)))

    val extended = (multimap.toSeq :+ (1 -> Set(2, 3, 4, 5)))
    val merged = extended.mergeSetMultimaps

    merged must haveTheSameElementsAs(Map(1 -> Set(1, 2, 3, 4, 5), 2 -> Set(2)))
  }

  "bag multimaps works fine" in {
    val list = List(1 -> 1, 1 -> 2, 1 -> 1, 2 -> 2)
    val multimap = list.toBagMultimap

    multimap must haveTheSameElementsAs(Map(1 -> Bag(1, 1, 2), 2 -> Bag(2)))

    val extended = (multimap.toSeq :+ (1 -> Bag(2, 3, 4, 5)))
    val merged = extended.mergeBagMultimaps

    merged must haveTheSameElementsAs(Map(1 -> Bag(1, 1, 2, 2, 3, 4, 5), 2 -> Bag(2)))
  }
}

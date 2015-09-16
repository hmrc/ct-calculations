package uk.gov.hmrc.ct.ct600a.v2.retriever

import uk.gov.hmrc.ct.box.retriever.BoxValues
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.B79
import uk.gov.hmrc.ct.ct600a.v2._

object CT600ABoxRetriever extends BoxValues[CT600ABoxRetriever]

trait CT600ABoxRetriever extends ComputationsBoxRetriever {

  def retrieveLPQ03(): LPQ03

  def retrieveLPQ04(): LPQ04

  def retrieveLPQ05(): LPQ05

  def retrieveLPQ06(): LPQ06

  def retrieveLPQ07(): LPQ07

  def retrieveLPQ08(): LPQ08

  def retrieveLPQ09(): LPQ09

  def retrieveLPQ10(): LPQ10

  def retrieveLP02(): LP02

  def retrieveLP03(): LP03

  def retrieveLP04(): LP04

  def retrieveLPQ01(): LPQ01 = LPQ01.calculate(this)

  def retrieveA1(): A1 = A1(retrieveLPQ09())

  def retrieveA2() = A2.calculate(this)

  def retrieveA3() = A3.calculate(this)

  def retrieveA4() = A4.calculate(this)

  def retrieveA5() = A5.calculate(this)

  def retrieveA6() = A6.calculate(this)

  def retrieveA7() = A7.calculate(this)

  def retrieveA8() = A8.calculate(this)

  def retrieveA8Inverse() = A8Inverse.calculate(this)

  def retrieveA9() = A9.calculate(this)

  def retrieveA9Inverse() = A9Inverse.calculate(this)

  def retrieveA10() = A10.calculate(this)

  def retrieveA10Inverse() = A10Inverse.calculate(this)

  def retrieveA11() = A11.calculate(this)

  def retrieveA11Inverse() = A11Inverse.calculate(this)

  def retrieveA12() = A12.calculate(this)

  def retrieveA13() = A13.calculate(this)

  def retrieveB79(): B79 = B79(retrieveA13())
}

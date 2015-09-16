package uk.gov.hmrc.ct.ct600a.v3.retriever

import uk.gov.hmrc.ct.box.retriever.BoxValues
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600a.v3.{A10, _}

object CT600ABoxRetriever extends BoxValues[CT600ABoxRetriever]

trait CT600ABoxRetriever extends ComputationsBoxRetriever {

  def retrieveLP04(): LP04

  def retrieveLPQ03(): LPQ03

  def retrieveLPQ04(): LPQ04

  def retrieveLPQ05(): LPQ05

  def retrieveLPQ06(): LPQ06

  def retrieveLPQ07(): LPQ07

  def retrieveLPQ08(): LPQ08

  def retrieveLPQ09(): LPQ09

  def retrieveLPQ10(): LPQ10

  def retrieveA10(): A10

  def retrieveA15(): A15 = A15.calculate(this)
  
  def retrieveA20(): A20 = A20.calculate(this)

  def retrieveA25(): A25

  def retrieveA30(): A30 = A30.calculate(this)

  def retrieveA35(): A35 = A35.calculate(this)

  def retrieveA40(): A40 = A40.calculate(this)

  def retrieveA45(): A45 = A45.calculate(this)

  def retrieveA55(): A55 = A55.calculate(this)

  def retrieveA55Inverse(): A55Inverse = A55Inverse.calculate(this)

  def retrieveA60(): A60 = A60.calculate(this)

  def retrieveA60Inverse(): A60Inverse = A60Inverse.calculate(this)
  
  def retrieveA65(): A65 = A65.calculate(this)

  def retrieveA65Inverse(): A65Inverse = A65Inverse.calculate(this)

  def retrieveA70(): A70 = A70.calculate(this)

  def retrieveA70Inverse(): A70Inverse = A70Inverse.calculate(this)

  def retrieveA75(): A75 = A75.calculate(this)

  def retrieveA80(): A80 = A80.calculate(this)
}

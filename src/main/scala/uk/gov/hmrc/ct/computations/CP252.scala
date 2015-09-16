package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}


case class CP252(value: Int) extends CtBoxIdentifier("Expenditure on designated environmentally friendly machinery and plant") with CtInteger

object CP252 extends Linked[CP79, CP252]{

  override def apply(source: CP79): CP252 = CP252(source.value.getOrElse(0))
}

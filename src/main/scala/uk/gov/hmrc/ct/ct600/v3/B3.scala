

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.UTR
import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Linked}


case class B3(value: String) extends CtBoxIdentifier(name = "Unique Taxpayer Reference") with CtString

object B3 extends Linked[UTR, B3] {

  override def apply(source: UTR): B3 = B3(source.value)
}

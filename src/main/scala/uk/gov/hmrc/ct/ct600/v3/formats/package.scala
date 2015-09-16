package uk.gov.hmrc.ct.ct600.v3

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats._

package object formats {

  implicit val b1Format: Format[B1] = new StringFormat[B1](B1.apply)

  implicit val b2Format: Format[B2] = new StringFormat[B2](B2.apply)

  implicit val b3Format: Format[B3] = new StringFormat[B3](B3.apply)

  implicit val B4Format: Format[B4] = new StringFormat[B4](B4.apply)

  implicit val B40Format: Format[B40] = new OptionalBooleanFormat[B40](B40.apply)

  implicit val B45Format: Format[B45] = new OptionalBooleanFormat[B45](B45.apply)

  implicit val B55Format: Format[B55] = new OptionalBooleanFormat[B55](B55.apply)

  implicit val B335Format: Format[B335] = new IntegerFormat[B335](B335.apply)

  implicit val BFQ1Format: Format[BFQ1] = new OptionalBooleanFormat[BFQ1](BFQ1.apply)

  implicit val B620Format: Format[B620] = new OptionalIntegerFormat[B620](B620.apply)

  implicit val B515Format: Format[B515] = new BigDecimalFormat[B515](B515.apply)

  implicit val B595Format: Format[B595] = new BigDecimalFormat[B595](B595.apply)

  implicit val B860Format: Format[B860] = new OptionalIntegerFormat[B860](B860.apply)
}

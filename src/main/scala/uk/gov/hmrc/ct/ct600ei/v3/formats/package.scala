package uk.gov.hmrc.ct.ct600ei.v3

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats.OptionalBooleanFormat

package object formats {
  implicit val dit001Format: Format[DIT001] = new OptionalBooleanFormat[DIT001](DIT001.apply)
  implicit val dit002Format: Format[DIT002] = new OptionalBooleanFormat[DIT002](DIT002.apply)
  implicit val dit003Format: Format[DIT003] = new OptionalBooleanFormat[DIT003](DIT003.apply)
}

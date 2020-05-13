

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._


case class B945(value: Option[String]) extends CtBoxIdentifier("Status of person authorising payment to other entity") with CtOptionalString with Input

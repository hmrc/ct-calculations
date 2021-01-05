/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class UTR(value: String) extends CtBoxIdentifier(name = "Unique Taxpayer Reference") with CtString with Input

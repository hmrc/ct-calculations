/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier}

case class CATO19(value: Boolean) extends CtBoxIdentifier with CtBoolean

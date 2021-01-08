/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class AC2(value: Option[String]) extends CtBoxIdentifier(name = "Company Name") with CtOptionalString with Input

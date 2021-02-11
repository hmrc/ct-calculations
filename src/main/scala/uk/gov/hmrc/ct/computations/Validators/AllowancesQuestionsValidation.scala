/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.Validators

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.CPQ7
import uk.gov.hmrc.ct.computations.capitalAllowanceAndSBA._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever


trait AllowancesQuestionsValidation {

  self: CtOptionalBoolean with ValidatableBox[ComputationsBoxRetriever] with CtBoxIdentifier =>

  def validateAgainstCPQ7(boxRetriever: ComputationsBoxRetriever, boxId: String, value: Option[Boolean]): Set[CtValidation] = {
    (boxRetriever.cpQ7(), value) match {
      case (CPQ7(Some(true)), None) => validateBooleanAsMandatory(boxId, this)
      case (CPQ7(Some(false)), Some(true)) => Set(CtValidation(Some(boxId), s"error.$boxId.notClaiming.required"))
      case _ => Set.empty
    }
  }

  val sbaLiveDate = LocalDate.parse("2020-04-01")

  def isSBALive(apEndDate: EndDate): Boolean =  {
    sbaApplies2019.isBefore(apEndDate.value) && LocalDate.now.isAfter(sbaLiveDate)
  }
}
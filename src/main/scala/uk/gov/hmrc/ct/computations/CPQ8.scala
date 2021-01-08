/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.AllowancesQuestionsValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CPQ8(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did the company cease trading?")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with AllowancesQuestionsValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    val boxId = "CPQ8"
    val companyCars = boxRetriever.cpQ7()
    val machineryOrPlant = boxRetriever.cpQ10()
    val structuresAndBuildings = boxRetriever.cpQ11()

    val validateMandatory = {
      if (companyCars.isTrue || machineryOrPlant.isTrue || structuresAndBuildings.isTrue) {
        validateBooleanAsMandatory(boxId ,this)
      } else validationSuccess
    }

    if(isSBALive(boxRetriever.cp2())) {
      validateMandatory
    }
    else
      {
        validateAgainstCPQ7(boxRetriever, boxId, value)
        validateMandatory
      }
  }

}
/*
 * Copyright 2015 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.box

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

trait ValidatableBox[T <: BoxRetriever] {

  val validNonForeignLessRestrictiveCharacters = "[A-Za-z0-9 ,\\.\\(\\)/&'\\-\"!%\\*_\\+:@<>\\?=;]*"
  val validNonForeignMoreRestrictiveCharacters = "[A-Za-z0-9 ,\\.\\(\\)/&'\\-\"]*"
  val SortCodeValidChars = """^[0-9]{6}$"""
  val AccountNumberValidChars = """^[0-9]{8}$"""

  def validate(boxRetriever: T): Set[CtValidation]

  protected def validateBooleanAsMandatory(boxId: String, box: CtOptionalBoolean): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateIntegerAsMandatory(boxId: String, box: CtOptionalInteger): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateStringAsMandatory(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case Some(x) if x.isEmpty => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateAsMandatory[U](box: CtValue[U] with CtBoxIdentifier): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(box.id), s"error.${box.id}.required"))
      case _ => Set()
    }
  }

  protected def validateStringAsMandatoryIfPAYEEQ1False(boxRetriever: CT600BoxRetriever, boxId: String, box: CtOptionalString): Set[CtValidation] = {
    val payeeq1 = boxRetriever.retrievePAYEEQ1()
    if (!payeeq1.value.getOrElse(true)) {
      validateStringAsMandatory(boxId, box)
    } else Set()
  }

  protected def validateAllFilledOrEmptyStrings(boxId: String, allBoxes: Set[CtString]): Set[CtValidation] = {
    val allEmpty = allBoxes.count(_.value.isEmpty) == allBoxes.size
    val allNonEmpty = allBoxes.count(_.value.nonEmpty) == allBoxes.size

    if(allEmpty || allNonEmpty) {
      Set()
    } else {
      Set(CtValidation(Some(boxId), s"error.$boxId.allornone"))
    }
  }

  protected def validateAllFilledOrEmptyStringsForBankDetails(boxRetriever: CT600BoxRetriever, boxId: String): Set[CtValidation] = {
    val bankDetailsBoxGroup:Set[CtString] = Set(
      boxRetriever.retrieveB920(),
      boxRetriever.retrieveB925(),
      boxRetriever.retrieveB930(),
      boxRetriever.retrieveB935()
    )
    validateAllFilledOrEmptyStrings(boxId, bankDetailsBoxGroup)
  }

  protected def validateStringAsBlank(boxId: String, box: CtOptionalString): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case Some(x) if x.isEmpty => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateDateAsMandatory(boxId: String, box: CtOptionalDate): Set[CtValidation] = {
    box.value match {
      case None => Set(CtValidation(Some(boxId), s"error.$boxId.required"))
      case _ => Set()
    }
  }

  protected def validateDateAsBlank(boxId: String, box: CtOptionalDate): Set[CtValidation] = {
    box.value match {
      case None => Set()
      case _ => Set(CtValidation(Some(boxId), s"error.$boxId.nonBlankValue"))
    }
  }

  protected def validateIntegerRange(boxId: String, box: CtOptionalInteger, min: Int = 0, max: Int): Set[CtValidation] = {
    box.value match {
      case Some(x) => {
        if (min <= x && x <= max) Set()
        else Set(CtValidation(Some(boxId), s"error.$boxId.outOfRange", Some(Seq(min.toString,max.toString))))
      }
      case _ => Set()
    }
  }

  protected def validatePositiveInteger(box: CtOptionalInteger with CtBoxIdentifier): Set[CtValidation] = {
    box.value match {
      case Some(x) if x>=0 => Set()
      case Some(x) => Set(CtValidation(Some(box.id), s"error.${box.id}.mustBePositive"))
      case _ => Set()
    }
  }

  protected def validateOptionalStringByRegex(boxId: String, box: CtOptionalString, regex: String): Set[CtValidation] = {
    box.value match {
      case Some(x) if x.nonEmpty => {
        if (x.matches(regex)) Set()
        else Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
      }
      case _ => Set()
    }
  }

  protected def validateStringByRegex(boxId: String, box: CtString, regex: String): Set[CtValidation] = {
    if (box.value.isEmpty || box.value.matches(regex)) Set()
    else Set(CtValidation(Some(boxId), s"error.$boxId.regexFailure"))
  }

  protected def validateStringByLength(boxId: String, box: CtString, min:Int, max:Int): Set[CtValidation] = {
     if(box.value.nonEmpty && box.value.size < min || box.value.size > max) {
       Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(Seq(min.toString,max.toString))))
     } else Set()
  }
}

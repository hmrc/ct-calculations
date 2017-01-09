/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.validation

import uk.gov.hmrc.ct.box.CtValidation


object CompoundBoxValidationHelper {
  @deprecated
  def contextualiseErrorKey(containerName: String, errorKey: String, index: Int): String = {
    val splitKey = errorKey.split('.')
    (splitKey.take(1) ++ Array("compoundList", containerName) ++ Array(index.toString) ++ splitKey.drop(1)).mkString(".")
  }

  def contextualiseError(owningBox: String, containerName: String, error: CtValidation, index: Int): CtValidation = {
    val splitKey = error.errorMessageKey.split('.')

    if(error.isGlobalError) {
      val errorMessage = (splitKey.take(2) ++ Array("compoundList", containerName) ++ Array(index.toString) ++ splitKey.drop(2)).mkString(".")
      error.copy(errorMessageKey = errorMessage)
    } else {
      val errorMessage = (splitKey.take(1) ++ Array("compoundList", containerName) ++ Array(index.toString) ++ splitKey.drop(1)).mkString(".")
      error.copy(boxId = Some(owningBox), errorMessageKey = errorMessage)
    }
  }
}

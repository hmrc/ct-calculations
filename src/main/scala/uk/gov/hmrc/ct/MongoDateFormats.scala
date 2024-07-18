/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.ct

import play.api.libs.json.{ Format, JsError, JsNumber, JsObject, JsString, JsSuccess, Reads, Writes, __ }

import java.time.{ Instant, LocalDate, LocalDateTime, ZoneOffset }
import scala.util.Try

object MongoDateFormats extends MongoDateFormats

trait MongoDateFormats {

  final val instantRead: Reads[Instant] = {
    case JsObject(map) if map.contains("$date") =>
      map("$date") match {
        case JsNumber(bigDecimal) =>
          JsSuccess(Instant.ofEpochMilli(bigDecimal.toLong))
        case JsObject(stringObject) =>
          if (stringObject.contains("$numberLong")) {
            JsSuccess(Instant.ofEpochMilli(BigDecimal(stringObject("$numberLong").as[JsString].value).toLong))
          } else {
            JsError("Unexpected LocalDateTime Format")
          }
        case JsString(dateValue) =>
          Try(JsSuccess(Instant.parse(dateValue))).getOrElse(JsError("Unexpected LocalDateTime Format"))
        case _ => JsError("Unexpected LocalDateTime Format")
      }
    case JsString(dateValue) =>
      Try(JsSuccess(Instant.parse(dateValue))).getOrElse(JsError("Unexpected LocalDateTime Format"))
    case _ => JsError("Unexpected LocalDateTime Format")
  }

  final val instantWrite: Writes[Instant] =
    Writes
      .at[String](__ \ "$date" \ "$numberLong")
      .contramap[Instant](_.toEpochMilli.toString)

  final val localDateTimeRead: Reads[LocalDateTime] =
    Reads
      .at[String](__ \ "$date" \ "$numberLong")
      .map(dateTime => LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime.toLong), ZoneOffset.UTC))

  final val localDateTimeWrite: Writes[LocalDateTime] =
    Writes
      .at[String](__ \ "$date" \ "$numberLong")
      .contramap[LocalDateTime](_.toInstant(ZoneOffset.UTC).toEpochMilli.toString)

  final val dateRead: Reads[LocalDate] = {
    case JsObject(map) if map.contains("$date") =>
      map("$date") match {
        case JsNumber(bigDecimal) =>
          JsSuccess(LocalDate.ofInstant(Instant.ofEpochMilli(bigDecimal.toLong), ZoneOffset.UTC))
        case JsObject(stringObject) =>
          stringObject.get("$numberLong") match {
            case Some(value) =>
              value.as[JsString].value.toLongOption match {
                case Some(epoch) => JsSuccess(LocalDate.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC))
                case None        => JsError("Unexpected LocalDate format in $numberLong")
              }
            case _ => JsError("Unexpected LocalDate Format")
          }
        case JsString(dateValue) =>
          Try(JsSuccess(LocalDate.parse(dateValue)))
            .getOrElse(
              Try(JsSuccess(LocalDate.ofInstant(Instant.parse(dateValue), ZoneOffset.UTC)))
                .getOrElse(JsError("Unexpected LocalDate Format"))
            )

        case _ => JsError("Unexpected LocalDate Format")
      }
    case JsString(dateValue) =>
      Try(JsSuccess(LocalDate.parse(dateValue)))
        .getOrElse(
          Try(JsSuccess(LocalDate.ofInstant(Instant.parse(dateValue), ZoneOffset.UTC)))
            .getOrElse(JsError("Unexpected LocalDate Format"))
        )
    case _ => JsError("Unexpected LocalDate Format")
  }

  final val dateWrite: Writes[LocalDate] =
    Writes
      .at[String](__ \ "$date" \ "$numberLong")
      .contramap[LocalDate](_.atStartOfDay(ZoneOffset.UTC).toInstant.toEpochMilli.toString)

  implicit val instantFormat: Format[Instant] = Format(instantRead, instantWrite)
  implicit val localDateTimeFormat: Format[LocalDateTime] = Format(localDateTimeRead, localDateTimeWrite)
  implicit val localDateFormat: Format[LocalDate] = Format(dateRead, dateWrite)
}

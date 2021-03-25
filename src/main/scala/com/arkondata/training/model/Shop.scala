package com.arkondata.training.model

import sangria.schema.BigDecimalType

case class Shop(id: Int,
                name: String,
                businessName: Option[String],
                address: String,
                phoneNumber: Option[String],
                email: Option[String],
                website: Option[String],
                lat: BigDecimal,
                long: BigDecimal,
                activityId: Option[Int],
                stratumId: Option[Int],
                shopTypeId: Option[Int],
               )
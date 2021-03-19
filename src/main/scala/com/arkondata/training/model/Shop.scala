package com.arkondata.training.model

import doobie.postgres.pgisimplicits._

case class Shop(id: Int,
                name: String,
                businessName: String,
                activityId: Int,
                stratumId: Int,
                address: String,
                phoneNumber: String,
                email: String,
                website: String,
                shopTypeId: Int
               )
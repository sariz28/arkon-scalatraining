package com.arkondata.training.model

case class Shop(id: Int,
                name: String,
                businessName: Option[String],
                address: String,
                phoneNumber: Option[String],
                email: Option[String],
                website: Option[String],
                lat: Float,
                long: Float,
                activityId: Option[Int],
                activityName: Option[String],
                stratumId: Option[Int],
                stratumName: Option[String],
                shopTypeId: Option[Int],
                shopTypeName: Option[String]
               ){

  def activity(): Activity = Activity(activityId, activityName)

  def stratum(): Stratum = Stratum(stratumId, stratumName)

  def shopType(): ShopType = ShopType(shopTypeId, shopTypeName)
}




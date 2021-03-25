package com.arkondata.training.schema

import cats.effect._
import cats.effect.implicits._
import com.arkondata.training.model.Shop
import com.arkondata.training.repository.MasterRepository
import sangria.schema._

object ShopType {

  val limitArg: Argument[Int] = Argument("limit", OptionInputType(IntType), defaultValue = 5)
  val radiusArg: Argument[Int] = Argument("radius", OptionInputType(IntType), defaultValue = 50)

  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], Shop] =
    ObjectType(
      name     = "ShopType",
      fieldsFn = () => fields(

        Field(
          name        = "id",
          fieldType   = IntType,
          description = Some("Shop id"),
          resolve     = _.value.id),

        Field(
          name        = "name",
          fieldType   = StringType,
          description = Some("Shop name"),
          resolve     = _.value.name),

        Field(
          name        = "businessName",
          fieldType   = OptionType(StringType),
          description = Some("business name"),
          resolve     = _.value.businessName),

        Field(
          name        = "activity",
          fieldType   = OptionType(ActivityType[F]),
          description = Some("activity"),
          resolve     = e => e.ctx.activityRepo.fetchById(e.value.activityId).toIO.unsafeToFuture),

        Field(
          name        = "stratum",
          fieldType   = OptionType(StratumType[F]),
          description = Some("stratum"),
          resolve     = e => e.ctx.stratumRepo.fetchById(e.value.stratumId).toIO.unsafeToFuture),

        Field(
          name        = "shopType",
          fieldType   = OptionType(ShopTypeType[F]),
          description = Some("shop type"),
          resolve     = e => e.ctx.shopTypeRepo.fetchById(e.value.shopTypeId).toIO.unsafeToFuture),

        Field(
          name        = "address",
          fieldType   = StringType,
          description = Some("address"),
          resolve     = _.value.address),

        Field(
          name        = "phoneNumber",
          fieldType   = OptionType(StringType),
          description = Some("phone number"),
          resolve     = _.value.phoneNumber),

        Field(
          name        = "email",
          fieldType   = OptionType(StringType),
          description = Some("email"),
          resolve     = _.value.email),

        Field(
          name        = "website",
          fieldType   = OptionType(StringType),
          description = Some("website"),
          resolve     = _.value.website),

        Field(
          name        = "lat",
          fieldType   = BigDecimalType,
          description = Some("latitude"),
          resolve     = _.value.lat),

        Field(
          name        = "long",
          fieldType   = BigDecimalType,
          description = Some("longitude"),
          resolve     = _.value.long),

        Field(
          name        = "nearbyShops",
          fieldType   = ListType(ShopType[F]),
          description = Some("near shops"),
          arguments   = limitArg :: Nil,
          resolve     = e => e.ctx.shopRepo.fetchNearbyShops(e.arg(limitArg),
                                                             e.value.lat,
                                                             e.value.long,
                                                             e.value.id).toIO.unsafeToFuture),

        Field(
          name        = "shopsInRadius",
          fieldType   = ListType(ShopType[F]),
          description = Some("shops in radius"),
          arguments   = radiusArg :: Nil,
          resolve     = e => e.ctx.shopRepo.fetchShopsInRadius(e.arg(radiusArg),
                                                                  e.value.lat,
                                                                  e.value.long,
                                                                  e.value.id).toIO.unsafeToFuture),
      )
    )
}

package com.arkondata.training.schema

import cats.effect.Effect
import com.arkondata.training.model.Shop
import com.arkondata.training.repository.ShopRepository
import sangria.schema._

object ShopType {

  def apply[F[_]: Effect]: ObjectType[ShopRepository[F], Shop] =
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
          fieldType   = OptionType(ActivityType.ActivityType),
          description = Some("comercial activity"),
          resolve     = _.value.activity()),

        Field(
          name        = "stratum",
          fieldType   = OptionType(StratumType.StratumType),
          description = Some("stratum"),
          resolve     = _.value.stratum()),

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
          name        = "shopType",
          fieldType   = OptionType(ShopTypeType.ShopTypeType),
          description = Some("shopType"),
          resolve     = _.value.shopType()),

        Field(
          name        = "lat",
          fieldType   = FloatType,
          description = Some("latitude"),
          resolve     = _.value.lat.toDouble),

        Field(
          name        = "long",
          fieldType   = FloatType,
          description = Some("longitude"),
          resolve     = _.value.long.toDouble),
      )
    )
}

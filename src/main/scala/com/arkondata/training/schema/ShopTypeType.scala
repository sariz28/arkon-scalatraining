package com.arkondata.training.schema

import com.arkondata.training.model.ShopType
import sangria.schema._
import cats.effect.Effect
import sangria.schema.{Field, IntType, ObjectType, OptionType, StringType, fields}

object ShopTypeType {

  def apply[F[_]: Effect]: ObjectType[Unit, ShopType] =
    ObjectType(
    "shopType",
    fields[Unit, ShopType](
      Field("id", OptionType(IntType), resolve = _.value.id),
      Field("name", OptionType(StringType), resolve = _.value.name),
    )
  )
}

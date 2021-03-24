package com.arkondata.training.schema

import com.arkondata.training.model.ShopType
import sangria.schema.{Field, IntType, ObjectType, OptionType, StringType, fields}

object ShopTypeType {
  val ShopTypeType: ObjectType[Unit, ShopType] = ObjectType(
    "shopType",
    "The shop type",

    fields[Unit, ShopType](
      Field("id", OptionType(IntType), resolve = _.value.id),
      Field("name", OptionType(StringType), resolve = _.value.name),
    )
  )
}

package com.arkondata.training.schema

import com.arkondata.training.model.Stratum
import sangria.schema.{Field, IntType, ObjectType, OptionType, StringType, fields}

object StratumType {
  val StratumType: ObjectType[Unit, Stratum] = ObjectType(
    "stratum",
    "The Comercial Activity",

    fields[Unit, Stratum](
      Field("id", OptionType(IntType), resolve = _.value.id),
      Field("name", OptionType(StringType), resolve = _.value.name),
    )
  )
}

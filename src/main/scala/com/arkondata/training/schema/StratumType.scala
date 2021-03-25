package com.arkondata.training.schema

import cats.effect.Effect
import com.arkondata.training.model.Stratum
import sangria.schema.{Field, IntType, ObjectType, OptionType, StringType, fields}

object StratumType {

  def apply[F[_]: Effect]: ObjectType[Unit, Stratum] =
    ObjectType(
      "stratum",
      fields[Unit, Stratum](
        Field("id", OptionType(IntType), resolve = _.value.id),
        Field("name", OptionType(StringType), resolve = _.value.name),
      )
    )
}

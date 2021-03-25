package com.arkondata.training.schema

import com.arkondata.training.model.Activity
import sangria.schema._
import cats.effect.Effect

object ActivityType{

  def apply[F[_]: Effect]: ObjectType[Unit, Activity] =
    ObjectType(
      "activity",
      fieldsFn = () => fields(
        Field("id", OptionType(IntType), resolve = _.value.id),
        Field("name", OptionType(StringType), resolve = _.value.name),
      )
    )
}

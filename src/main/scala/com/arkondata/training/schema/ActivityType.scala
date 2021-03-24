package com.arkondata.training.schema

import com.arkondata.training.model.Activity
import sangria.schema._

object ActivityType{

  val ActivityType: ObjectType[Unit, Activity] = ObjectType(
    "activity",
    "The Comercial Activity",

    fields[Unit, Activity](
      Field("id", OptionType(IntType), resolve = _.value.id),
      Field("name", OptionType(StringType), resolve = _.value.name),
      )
  )
}

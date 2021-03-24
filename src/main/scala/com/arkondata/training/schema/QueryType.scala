package com.arkondata.training.schema

import cats.effect._
import cats.effect.implicits._
import com.arkondata.training.repository.ShopRepository
import sangria.schema._

object QueryType {

  val limitArg: Argument[Int] = Argument("limit", OptionInputType(IntType), defaultValue = 50)
  val offsetArg: Argument[Int] = Argument("offset", OptionInputType(IntType), defaultValue = 0)
  val idArg: Argument[Int] = Argument("id", IntType)

  def apply[F[_]: Effect]: ObjectType[ShopRepository[F], Unit] =
    ObjectType(
      name  = "Query",
      fields = fields(
        Field(
          name        = "shops",
          fieldType   = ListType(ShopType[F]),
          description = Some("Returns all Shops."),
          arguments   = limitArg :: offsetArg :: Nil,
          resolve     = c =>  c.ctx.fetchAll(c.arg(limitArg), c.arg(offsetArg)).toIO.unsafeToFuture
        ),
        Field(
          name        = "shop",
          fieldType   = OptionType(ShopType[F]),
          description = Some("Returns a Shop by id."),
          arguments   = idArg:: Nil,
          resolve     = c =>  c.ctx.fetchById(c.arg(idArg)).toIO.unsafeToFuture
        ),

      )
    )

  def schema[F[_]: Effect]: Schema[ShopRepository[F], Unit] =
    Schema(QueryType[F])

}

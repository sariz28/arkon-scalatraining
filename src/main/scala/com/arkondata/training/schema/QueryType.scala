package com.arkondata.training.schema

import cats.effect._
import cats.effect.implicits._
import com.arkondata.training.repository.MasterRepository
import sangria.schema._

object QueryType {

  val limitArg: Argument[Int] = Argument("limit", OptionInputType(IntType), defaultValue = 50)
  val offsetArg: Argument[Int] = Argument("offset", OptionInputType(IntType), defaultValue = 0)
  val latArg: Argument[BigDecimal] = Argument("lat", BigDecimalType)
  val longArg: Argument[BigDecimal] = Argument("long", BigDecimalType)
  val radiusArg: Argument[Int] = Argument("radius", OptionInputType(IntType), defaultValue = 50)


  def apply[F[_]: Effect]: ObjectType[MasterRepository[F], Unit] =
    ObjectType(
      name  = "Query",
      fields = fields(
        Field(
          name        = "shops",
          fieldType   = ListType(ShopType[F]),
          description = Some("Returns all Shops."),
          arguments   = limitArg :: offsetArg :: Nil,
          resolve     = c =>  c.ctx.shopRepo.fetchAll(c.arg(limitArg),
            c.arg(offsetArg)).toIO.unsafeToFuture
        ),
        Field(
          name        = "nearbyShops",
          fieldType   = ListType(ShopType[F]),
          description = Some("near shops"),
          arguments   = limitArg ::  latArg::  longArg :: Nil,
            resolve     = e => e.ctx.shopRepo.fetchNearbyShops(e.arg(limitArg),
            e.arg(latArg),
            e.arg(longArg)).toIO.unsafeToFuture
        ),

        Field(
          name        = "shopsInRadius",
          fieldType   = ListType(ShopType[F]),
          description = Some("shops in radius"),
          arguments   = radiusArg  ::  latArg::  longArg :: Nil,
          resolve     = e => e.ctx.shopRepo.fetchShopsInRadius(e.arg(radiusArg),
            e.arg(latArg),
            e.arg(longArg)).toIO.unsafeToFuture),
      )
    )

  def schema[F[_]: Effect]: Schema[MasterRepository[F], Unit] =
    Schema(QueryType[F])

}

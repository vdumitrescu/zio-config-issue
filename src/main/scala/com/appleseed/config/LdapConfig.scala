package com.appleseed.config

import zio._

final case class LdapConfig(
    url: String,
    user: String
)

object LdapConfig {
  import zio.config._
  import zio.config.magnolia._

  val layer: ZLayer[Any, Config.Error, LdapConfig] = ZLayer.fromZIO(
    ZIO.config[LdapConfig](deriveConfig[LdapConfig].nested("ldapConfig"))
  )
}

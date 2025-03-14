package com.appleseed

import com.appleseed.config._
import com.appleseed.service._
import com.typesafe.config.ConfigFactory
import zio.*
import zio.config.typesafe.TypesafeConfigProvider
import zio.logging.backend.SLF4J

object MainApp extends ZIOAppDefault:

  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    (for
      _       <- ZIO.succeed(println("Hello, ZIO Config!"))
      success <- LdapService.readAttributes("john.appleseed")
      _       <- ZIO.log("Success: " + success)
    yield ()).provide(
      LdapConfig.layer,
      LdapServiceImpl.live
    )

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    ZLayer.fromZIO {
      for {
        env    <- System.env("ENV").map(_.getOrElse("dev"))
        config <- ZIO.attempt(ConfigFactory.parseResources(s"application.$env.conf"))
        ldapUrl = config.getConfig("ldapConfig").getString("url")
        ldapUsr = config.getConfig("ldapConfig").getString("user")
        _      <- ZIO.log(s"Configured LDAP $ldapUsr@$ldapUrl")
      } yield Runtime.setConfigProvider(TypesafeConfigProvider.fromTypesafeConfig(config))
        >>> Runtime.removeDefaultLoggers >>> SLF4J.slf4j
    }

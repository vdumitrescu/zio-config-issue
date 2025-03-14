# An issue with ZIO Config

I have multiple configuration files, one for each environment, in a "fat jar".
If I read the configuration using `TypesafeConfigProvider.fromResourcePath()`, it works as expected.
However, if I try to parse the Typesafe Config and then use `TypesafeConfig.fromTypesafeConfig()`, it fails.

## Steps to reproduce
Build the "fat jar" with `sbt assembly`, and then run it with:

```bash
java -jar target/scala-3.3.5/zio-config-issue-assembly-0.1.0-SNAPSHOT.jar
```

It logs:
```
Configured LDAP dev@ldap://dev.appleseed.com:389
```
followed by
```
Exception in thread "zio-fiber-980481300" zio.Config$Error$And:
(((Missing data at ldapConfig.url: Expected LDAPCONFIG_URL to be set in the environment) or
  (Missing data at ldapConfig.url: Expected ldapConfig.url to be set in properties)) and
 ((Missing data at ldapConfig.user: Expected LDAPCONFIG_USER to be set in the environment) or
  (Missing data at ldapConfig.user: Expected ldapConfig.user to be set in properties)))
        at com.appleseed.config.LdapConfig.layer(LdapConfig.scala:15)
        at com.appleseed.config.LdapConfig.layer(LdapConfig.scala:16)
        at com.appleseed.MainApp.run(MainApp.scala:13)
```

If executed with the `test` value, logging out of the `TypesafeConfig` object works as expected, but exception from ZioConfig is the same.

```bash
ENV=test java -jar target/scala-3.3.5/zio-config-issue-assembly-0.1.0-SNAPSHOT.jar
```

logs

`Configured LDAP test@ldap://test.appleseed.com:389`

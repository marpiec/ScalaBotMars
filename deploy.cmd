mkdir c:\mar\scala\scalatron\Scalatron\bots\Mars
cp target\scala-bot-mars-1.0-SNAPSHOT.jar c:\mar\scala\scalatron\Scalatron\bots\Mars\ScalatronBot.jar
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Duser.dir:c:\mar\scala\scalatron\Scalatron\bin -jar c:\mar\scala\scalatron\Scalatron\bin\Scalatron.jar  -browser no

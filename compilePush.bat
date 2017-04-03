call ./gradlew build
call copy "C:\Users\Brandon\Desktop\2nd Year Term 2\2XB3\CarForMe\build\libs\myapp-0.9.0.war" "C:\Users\Brandon\Desktop\2nd Year Term 2\2XB3\bluemix\get-started-tomcat\target\myapp-0.9.0.war"
call cd "C:\Users\Brandon\Desktop\2nd Year Term 2\2XB3\bluemix\get-started-tomcat"
call cf push -b https://github.com/cloudfoundry/java-buildpack.git

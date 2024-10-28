# Use the official Tomcat image as the base image
FROM tomcat:10.1-jdk21-corretto

# Set the working directory inside the container
WORKDIR /usr/local/tomcat/webapps

# Copy the WAR file from the target directory into the Tomcat webapps directory
COPY target/*.war /usr/local/tomcat/webapps/yourapp.war

# Expose the application port (adjust if necessary)
EXPOSE 8085

# Start Tomcat server
CMD ["catalina.sh", "run"]

# The Artificial News

A React/Spring Boot web app that uses AI to generate funny little news articles.
 
Try it here at [justinmartz.dev/ArtificialNews](https://justinmartz.dev/ArtificialNews)

## Contents
[Development Requirements](#development-requirements)

[Running Locally](#running-locally)

[Building and Deploying](#building-and-deploying)

[Appendix: Links and Resources](#appendix-links-and-resources)

[License](#license)

## Development Requirements
* OpenAI API key
* Command line environment running either bash or zsh
* To deploy, you will need Debian 12 running Tomcat 10 and NGINX 1.22.1

## Running Locally
### React - Command Line
From the `artificial-news-react/` subdirectory, start the React development server (Vite) with
```npm run dev```

### Spring Boot - Command Line
Store your API key as an environment variable in the shell with ```export API_KEY=YOUR-API-KEY-GOES-HERE```

From the `artificial-news-spring-boot/` subdirectory, start the Spring Boot application with ```./gradlew bootRun --args='--spring.profiles.active=dev'```

### Spring Boot - IntelliJ IDEA

Open the Gradle tool window and find the `bootRunDev` task under Tasks -> application, then right-click on it. Select `Modify Run Configuration...` and in the `Environment variables:` field add your OpenAI API key in the format of `API_KEY=YOUR-API-KEY-GOES-HERE` and click OK. Run the `bootRunDev` Gradle task (not `bootRun`)

## Building and Deploying
### Building the React app
From the `artificial-news-react/` subdirectory, build the React app with
```npm run build```

From the same subdirectory, copy the build files into the Spring Boot app with ```cp -rp dist/* ../artificial-news-spring-boot/src/main/resources/static``` 
### Building the Spring Boot app

Verify the React app was copied into the `src/main/resources/static` directory. Run the `war` Gradle task. When finished it will leave `ArtificialNews.war` in the directory above `artificial-news/`

### Deploying
I built this to run on Debian 12 with Tomcat 10 and NGINX 1.22.1 so that's what I'll outline here. You can most definitely use these deployment steps as a starting point to run on older/newer versions of Debian, or a different distribution altogether, and probably with a different version of Tomcat and/or NGINX as well.

Install NGINX using the how-to here: https://docs.vultr.com/how-to-install-nginx-webserver-on-debian-12

 
 This will be the proxy server to route HTTPS requests from the Internet to Tomcat running on http://localhost:8080/ArtificialNews

Add the following to `/etc/nginx/conf.d/yourdomain.org.conf`:

```     
location /ArtificialNews {
    proxy_pass http://localhost:8080;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	proxy_set_header Access-Control-Allow-Origin $http_origin;
	proxy_set_header X-Forwarded-Proto $scheme;
}
```

Make sure your configuration is valid with 

```sudo nginx -t```

then restart NGINX with

```sudo systemctl restart nginx```

Install Tomcat 10 with the how-to here: https://www.howtoforge.com/how-to-install-apache-tomcat-10-on-debian-12/

Don't worry about creating the Tomcat Administrator. Once installed, add the following to `/usr/share/tomcat10/bin/setenv.sh`

```
export API_KEY=YOUR-SUPER-SECRET-API-KEY
export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=prod"
```

Create a directory called `/etc/systemd/system/tomcat10.service.d` and create a file in that directory called `override.conf`

In `override.conf` add the following

```
[Service]
ReadWritePaths=/var/lib/tomcat10/uploads
```

This gives Tomcat write access for that directory (instead of the Spring Boot app's `/src/main/resources/static`).

## Appendix: Links and Resources
* I made my first open source contribution in [Spring AI 1.0.0 M7](https://spring.io/blog/2025/04/10/spring-ai-1-0-0-m7-released#contributors) while working on this project.
* [This article](https://stackoverflow.com/questions/56827735/how-to-allow-tomcat-war-app-to-write-in-folder) helped me figure out that Tomcat on Debian is sandboxed and does not have write access to directories outside of the intial configuration.

## License
[Unlicense](https://unlicense.org)
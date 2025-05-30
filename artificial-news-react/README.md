# The Artificial News

## Local Dev

## To-do
- Better loading screen
- Article list page
- GitHub link
- Add share icon & view all icon to each article
- Open Graph tags
- Environment variables 
- Responsive
- Accessible
- Streaming

## DevOps
So Debian runs Tomcat in a sandbox and won't allow apps to write to the filesystem unless explicitly told where. I had to edit the Tomcat config at /etc/systemd/system/multi-user.target.wants/tomcat10.service by adding
ReadWritePaths=/var/lib/tomcat10/uploads/

## Building
- Build the React app:
<code>npm run build</code> in the React app root directory (e.g., <code>/artificial-news</code>)
- Copy the React app into the Spring Boot project's <code>resources/static</code> directory: <code>cp -rp dist/* ../artificial-news-spring-boot/src/main/resources/static</code>
- Build the war file: <code>./gradlew war</code> (not bootWar) in the Spring Boot project's root directory (e.g., <code>/artificial-news-spring-boot</code>), or run the gradle war task (not bootWar) in the IDE
- Copy the war file to the sever: <code>scp -rp -i ~/your_ssh_key ./ArtificialNews.war dev@123.45.67.89:/var/lib/tomcat10/webapps/</code>

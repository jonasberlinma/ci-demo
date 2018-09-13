FROM node
MAINTAINER Jonas Berlin <jonas@theberlins.org>
ADD src/main/java/org/theberlins/citest/ping.js /home/node/ping.js

EXPOSE 8080

ENTRYPOINT node /home/node/ping.js

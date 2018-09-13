FROM node
MAINTAINER Jonas Berlin <jonas@theberlins.org>

EXPOSE 8080

ENTRYPOINT node /home/node/mysite.js

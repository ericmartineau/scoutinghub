#!/bin/sh

git pull && grails clean && grails maven-deploy --repository=martytime && grails increment-version && git add -u && git commit && git push origin master

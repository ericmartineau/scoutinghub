#!/bin/sh

grails clean && grails maven-deploy --repository=scoutinghub && grails increment-version && git add -u && git commit && git push origin master

#!/bin/sh

grails maven-deploy --repository=scoutinghub && grails increment-version && git add -u && git commit

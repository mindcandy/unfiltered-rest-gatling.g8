#!/usr/bin/env python
# A very simple fabric deploy script. When run from the directory containing the script,
# will search for any jars in the target folder (produced via 'sbt assembly' and upload
# them to the specified host

from __future__ import with_statement
from fabric.api import *

env.user = '<youruser>'

if env.hosts == []:
    env.hosts = ['<yourhost>']

def upload():
    print 'Uploading application to server'
    with lcd('../$project$-server/target'):
        put('*.jar', '/opt/$organization;format="word,lower"$/$project$/', use_sudo=False)

def deploy(appname=None, toolmode=None):
    print 'Rollout to host: ', env.hosts
    upload()

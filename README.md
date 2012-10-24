# Description
unfiltered-rest-gatling Giter8 Template

A [giter8][giter8] template which builds a working skeleton [sbt] project with an [unfiltered] [netty] standalone REST server wrapped with the apache-commons [daemon] library.
Also provides a sample [fabric] deploy script, and is setup with [gatling] load testing.

Provides a working REST server which will output 'hello, project' when the endpoint is hit, with some example tests.

The generated project is also configured with the [sbt-idea][sbtidea], [sbt-assembly][sbtassembly], [sbt-release][sbtrelease], and [sbt-dependency-graph][sbtdepgraph] plugins.

# Usage
First install g8 by following the [readme](http://github.com/n8han/giter8#readme).

    g8 mindcandy/unfiltered-rest-gatling

There are some properties that need to be specified (most can be left default)

    name                            => The main projects name
    project                         => The name prepended to each of the sub projects
    organization                    => Used as the basis for packages
    version                         => Version number to use
    scala_version                   => Version of scala
    unfiltered_version              => Version of unfiltered
    dispatch_version                => Version of dispatch
    sbt_dependency_graph_version    => Version of sbt-dependency-graph
    sbt_assembly_version            => Version of sbt-assembly
    verbatim                        => Files to ignore during g8 processing

After g8 has finished:

    cd <name>
    sbt test
    sbt gen-idea
    sbt run

Now open your browser and go to http://localhost:8889/'project' and you should get a "Hello, project" message :)
## Try some load testing

    sbt run
    launch new terminal in project directory
    sbt "project <project>-server" "test-load:run"
    choose option for 'BatchEngine'
    view generated results under project/gatling/results

## Run from start script
First configure jsvc on your machine by following the instructions [here](http://commons.apache.org/daemon/jsvc.html). Then:

    cd <name>
    sbt assembly
    cd bin
    chmod +x start.sh
    ./start.sh (remember to stop the server when finished with ../bin/stop.sh)

# Generated Project Structure
    * <name>
        * <project>-server          // Controls the server
        * <project>-resources       // Containing the unfiltered netty request plan
        * <project>-core            // Business logic
        * bin                       // Start, Stop scripts
        * deploy                    // Sample fabric file
        * gatling
            * results               //Output of gatling results
        * project                   //sbt config

'name' and 'project' are the properties defined during the template execution.

# Author
Tim Bennett <tim.bennett@mindcandy.com>

# Licence
Copyright 2012 Mind Candy Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[giter8]: https://github.com/n8han/giter8
[sbt]: http://github.com/harrah/xsbt/
[unfiltered]: http://unfiltered.databinder.net/Unfiltered.html
[netty]: https://netty.io/
[daemon]: http://commons.apache.org/daemon/
[fabric]: http://docs.fabfile.org/en/1.4.3/
[gatling]: http://gatling-tool.org/
[sbtidea]: https://github.com/mpeltonen/sbt-idea
[sbtassembly]: https://github.com/sbt/sbt-assembly
[sbtrelease]: https://github.com/sbt/sbt-release
[sbtdepgraph]: https://github.com/jrudolph/sbt-dependency-graph
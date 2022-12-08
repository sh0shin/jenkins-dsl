import groovy.json.JsonSlurper

def reposJSON = new JsonSlurper().parseText(readFileFromWorkspace('repos.json'))

reposJSON.repos.each {
    createPipeline(it)
}

void createPipeline(it) {

    def jobname = it.jobname
    def gitrepo = it.gitrepo
    def gitbranch = it.gitbranch
    def desc = it.description
    pipelineJob(jobname) {
        description(desc)

        parameters {
          choiceParam('FooBar', ['SNAFU (default)', 'SUSFU'], 'Select the FooBar')
        }

        definition {
            cpsScm {
                scm {
                    git {
                        branch(gitbranch)

                        extensions {
                            cloneOption {
                                depth(1)
                            }
                            shallow(true)
                        }

                        remote {
                            url(gitrepo)
                        }
                    }
                }
            }
        }
    }
}

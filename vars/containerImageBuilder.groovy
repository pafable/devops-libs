def call(final Closure body) {
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body()

    final String tag = 'debug' // kaniko tag needs to be debug for it to work in jenkins
    final String dockerHubUser = 'boomb0x'
    final String destination = "${dockerHubUser}/${body.appName}:${body.appVersion}"
    final String cronSchedule = body.cron ? body.cron : null

    pipeline {
        agent {
            kubernetes {
                yaml """
                    kind: Pod
                    spec:
                      containers:
                      - name: kaniko
                        image: gcr.io/kaniko-project/executor:${tag}
                        imagePullPolicy: Always
                        command:
                        - sleep
                        args:
                        - 9999999
                        volumeMounts:
                          - name: jenkins-docker-cfg
                            mountPath: /kaniko/.docker
                      volumes:
                      - name: jenkins-docker-cfg
                        projected:
                          sources:
                          - secret:
                              name: docker-credentials
                              items:
                                - key: .dockerconfigjson
                                  path: config.json
                """
            }
        }

        options {
            ansiColor('xterm')
            timeout(
                time: 20,
                unit: 'MINUTES'
            )
        }

        triggers {
            cron("${cronSchedule}")
        }

        stages {
            stage('building image') {
                steps {
                    container(
                        name: 'kaniko',
                        shell: '/busybox/sh'
                    ) {
                        sh """
                            #!/busybox/sh

                            /kaniko/executor --context `pwd` \
                                --dockerfile=${body.dockerFilePath} \
                                --destination ${destination}
                        """
                    }
                }
            }
        }
    }
}

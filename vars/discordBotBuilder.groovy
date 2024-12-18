def call(final Closure body) {
    final String tag = 'debug'
    final String dockerHubUser = 'boomb0x'
    final String appName = 'discord-bot-3'
    final String appVersion = '0.0.1'
    final String destination = "${dockerHubUser}/${appName}:${appVersion}"

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

        stages {
            stage('building image') {
                steps {
                    container(name: 'kaniko', shell: '/busybox/sh') {
                        sh """
                            #!/busybox/sh

                            /kaniko/executor --context `pwd` \
                                --dockerfile=Dockerfile \
                                --destination ${dockerHubUser}/${appName}:${appVersion}
                        """
                    }
                }
            }
        }
    }
}
import com.pafable.IsoImagesArgs
import com.pafable.PipelineArgs
import com.pafable.SeedjobArgs

def call(final Closure body) {
    final String agentLabel = 'jenkins-agent'
    final def distros = IsoImagesArgs.getDistros()

    pipeline {
        agent {
          label "${agentLabel}"
        }

        options {
            ansiColor('xterm')
            timeout(
                time: 5,
                unit: 'MINUTES'
            )
        }

        stages {
            stage('seeding') {
                steps {
                    script {
                        println distros
                        println distros.getClass()

                        for (def i : distros) {
                            println i
                        }

                        jobDsl removedConfigFilesAction: 'DELETE',
                        removedJobAction: 'DELETE',
                        removedViewAction: 'DELETE',
                        scriptText: """
                    // apps-deployer
                            pipelineJob("${SeedjobArgs.APPS_DEPLOYER.name}") {
                                description("${SeedjobArgs.APPS_DEPLOYER.desc}")
                                parameters {
                                    choiceParam(
                                        "${PipelineArgs.taskName}",
                                        "${PipelineArgs.taskActions}",
                                        "${PipelineArgs.taskDesc}"
                                    )
                                }
                                definition {
                                    cpsScm {
                                        scm {
                                            git {
                                                remote {
                                                    url("${SeedjobArgs.APPS_DEPLOYER.repo}")
                                                }
                                                branch("${SeedjobArgs.APPS_DEPLOYER.branch}")
                                            }
                                        }
                                        scriptPath("${SeedjobArgs.APPS_DEPLOYER.path}")
                                        lightweight()
                                    }
                                }
                                properties {
                                    disableConcurrentBuilds()
                                    disableResume()
                                }
                            }

                    // discord-bot-builder
                            pipelineJob("${SeedjobArgs.DISCORD_BOT_BUILDER.name}") {
                                description("${SeedjobArgs.DISCORD_BOT_BUILDER.desc}")
                                definition {
                                    cpsScm {
                                        scm {
                                            git {
                                                remote {
                                                    url("${SeedjobArgs.DISCORD_BOT_BUILDER.repo}")
                                                }
                                                branch("${SeedjobArgs.DISCORD_BOT_BUILDER.branch}")
                                            }
                                        }
                                        scriptPath("${SeedjobArgs.DISCORD_BOT_BUILDER.path}")
                                        lightweight()
                                    }
                                }
                                properties {
                                    disableConcurrentBuilds()
                                    disableResume()
                                }
                            }

                    // dns-updater
                            pipelineJob("${SeedjobArgs.DNS_UPDATER.name}") {
                                description("${SeedjobArgs.DNS_UPDATER.desc}")
                                parameters {
                                    "${PipelineArgs.actionsParams()}"
                                }
                                definition {
                                    cpsScm {
                                        scm {
                                            git {
                                                remote {
                                                    url("${SeedjobArgs.DNS_UPDATER.repo}")
                                                }
                                                branch("${SeedjobArgs.DNS_UPDATER.branch}")
                                            }
                                        }
                                        scriptPath("${SeedjobArgs.DNS_UPDATER.path}")
                                        lightweight()
                                    }
                                }
                                properties {
                                    disableConcurrentBuilds()
                                    disableResume()
                                }
                            }

                    // jenkins-agent-builder
                            pipelineJob("${SeedjobArgs.JENKINS_AGENT_BUILDER.name}") {
                                description("${SeedjobArgs.JENKINS_AGENT_BUILDER.desc}")
                                definition {
                                    cpsScm {
                                        scm {
                                            git {
                                                remote {
                                                    url("${SeedjobArgs.JENKINS_AGENT_BUILDER.repo}")
                                                }
                                                branch("${SeedjobArgs.JENKINS_AGENT_BUILDER.branch}")
                                            }
                                        }
                                        scriptPath("${SeedjobArgs.JENKINS_AGENT_BUILDER.path}")
                                        lightweight()
                                    }
                                }
                                properties {
                                    disableConcurrentBuilds()
                                    disableResume()
                                }
                            }

                    // k3s-infra-factory
                            pipelineJob("${SeedjobArgs.K3S_INFRA_FACTORY.name}") {
                                description("${SeedjobArgs.K3S_INFRA_FACTORY.desc}")
                                parameters {
                                    "${PipelineArgs.actionsParams()}"
                                }
                                definition {
                                    cpsScm {
                                        scm {
                                            git {
                                                remote {
                                                    url("${SeedjobArgs.K3S_INFRA_FACTORY.repo}")
                                                }
                                                branch("${SeedjobArgs.K3S_INFRA_FACTORY.branch}")
                                            }
                                        }
                                        scriptPath("${SeedjobArgs.K3S_INFRA_FACTORY.path}")
                                        lightweight()
                                    }
                                }
                                properties {
                                    disableConcurrentBuilds()
                                    disableResume()
                                }
                            }

                    // packy
                            pipelineJob("${SeedjobArgs.PACKY.name}") {
                                description("${SeedjobArgs.PACKY.desc}")
                                parameters {
                                    "${IsoImagesArgs.distoParams()}"
                                    choiceParam(
                                        'ISO_NAME',
                                        [
                                            'OracleLinux-R9-U4-x86_64-boot.iso',
                                            'Rocky-9.4-x86_64-boot.iso',
                                            'debian-12.7.0-amd64-netinst.iso',
                                            'ubuntu-24.04.1-live-server-amd64.iso'
                                        ],
                                        'iso names'
                                    )
                                    choiceParam(
                                        'PROXMOX_NODE',
                                        [
                                            'behemoth',
                                            'kraken',
                                            'leviathan'
                                        ],
                                        'proxmox nodes'
                                    )
                                    stringParam(
                                        'TEMPLATE_NAME',
                                        null,
                                        'template name'
                                    )
                                }
                                definition {
                                    cpsScm {
                                        scm {
                                            git {
                                                remote {
                                                    url("${SeedjobArgs.PACKY.repo}")
                                                }
                                                branch("${SeedjobArgs.PACKY.branch}")
                                            }
                                        }
                                        scriptPath("${SeedjobArgs.PACKY.path}")
                                        lightweight()
                                    }
                                }
                                properties {
                                    disableConcurrentBuilds()
                                    disableResume()
                                }
                            }

                    // rpm-server
                            pipelineJob("${SeedjobArgs.RPM_SERVER.name}") {
                                description("${SeedjobArgs.RPM_SERVER.desc}")
                                parameters {
                                    "${PipelineArgs.actionsParams()}"
                                }
                                definition {
                                    cpsScm {
                                        scm {
                                            git {
                                                remote {
                                                    url("${SeedjobArgs.RPM_SERVER.repo}")
                                                }
                                                branch("${SeedjobArgs.RPM_SERVER.branch}")
                                            }
                                        }
                                        scriptPath("${SeedjobArgs.RPM_SERVER.path}")
                                        lightweight()
                                    }
                                }
                                properties {
                                    disableConcurrentBuilds()
                                    disableResume()
                                }
                            }
                        """
                    }
                }
            }
        }
    }
}
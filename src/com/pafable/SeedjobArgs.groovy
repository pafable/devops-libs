package com.pafable


final String platformBranch = 'refs/heads/master'
final String platformRepo = 'https://github.com/pafable/k8s-platform.git'

enum SeedjobArgs {
    APPS_DEPLOYER(
        'apps-deployer',
        'Apps deployer',
        'cicd/apps/Jenkinsfile',
        platformRepo,
        platformBranch
    ),
    DISCORD_BOT_BUILDER(
        'discord-bot-builder',
        'Discord Bot Builder',
        'cicd/builder/Jenkinsfile',
        'https://github.com/pafable/discord-bot-3',
        'refs/heads/main'
    ),
    DNS_UPDATER(
        'dns-updater',
        'Updates DNS records',
        'cicd/dns/Jenkinsfile',
        platformRepo,
        platformBranch
    ),
    JENKINS_AGENT_BUILDER(
        'jenkins-agent-builder',
        'Jenkins agent builder',
        'cicd/agent-builder/Jenkinsfile',
        platformRepo,
        platformBranch
    ),
    K3S_INFRA_FACTORY(
        'k3s-infra-factory',
        'Builds k3s infra',
        'cicd/k3s/Jenkinsfile',
        platformRepo,
        platformBranch
    ),
    PACKY(
        'packy',
        'Builds images with Hashicorp Packer',
        'cicd/packy/Jenkinsfile',
        platformRepo,
        platformBranch
    ),
    RPM_SERVER(
        'rpm-server',
        'Builds rpm server infra',
        'cicd/rpm-srv/Jenkinsfile',
        platformRepo,
        platformBranch
    )

    final String name
    final String desc
    final String path
    final String repo
    final String branch

    SeedjobArgs(
        final String name,
        final String desc,
        final String path,
        final String repo,
        final String branch
    )
    {
        this.name = name
        this.desc = desc
        this.path = path
        this.repo = repo
        this.branch = branch
    }
}
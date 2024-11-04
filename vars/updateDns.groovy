def call() {
    build(
        job: 'dns-updater', 
        parameters: [string(name: 'ACTIONS', value: 'create')]
    )
}
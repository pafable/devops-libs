package com.pafable

class PipelineArgs {
    public static final ArrayList<String> taskActions = [
        'create',
        'destroy'
    ]

    public static final String taskDesc = 'Create or destroy actions'

    public static final String taskName = 'ACTIONS'

    public static final ArrayList<String> distros = [
        'debian',
        'oracle',
        'rocky',
        'ubuntu'
    ]

    public static final ArrayList<String> isos = [
        'debian-iso',
        'oracle-iso',
        'rocky-iso',
        'ubuntu-iso'
    ]

    public static final String actionsParams()
    {
        return "choiceParam(\"${PipelineArgs.taskName}\", \"${PipelineArgs.taskActions}\", \"${PipelineArgs.taskDesc}\")"
    }
}
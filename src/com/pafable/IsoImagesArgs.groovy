package com.pafable

enum IsoImagesArgs {
    DEBIAN(
        'debian',
        'debian-12.7.0-amd64-netinst.iso'
    ),
    ORACLE(
        'oracle',
        'OracleLinux-R9-U4-x86_64-boot.iso'
    ),
    ROCKY(
        'rocky',
        'Rocky-9.4-x86_64-boot.iso'
    ),
    UBUNTU(
        'ubuntu',
        'ubuntu-24.04.1-live-server-amd64.iso'
    )

    final String distro
    final String iso

    IsoImagesArgs(
        final String distro,
        final String iso
    ) {
        this.distro = distro
        this.iso = iso
    }

    public static final ArrayList<String> getDistros() {
        return IsoImagesArgs.values().collect { it.distro }.sort()
    }

    public static final ArrayList<String> getIsos() {
        return IsoImagesArgs.values().collect { it.iso }.sort()
    }

    public static final String distoParams() {
        return "choiceParam(DISTRO, ${IsoImagesArgs.getDistros()}, ${IsoImagesArgs.getIsos()})"
    }
}
package com.azimo.tool.utils;

/**
 * Created by tomasz.konieczny on 20/02/2017.
 */

public enum Apps {

    PL("pl.tablica", ":flag-pl: OLX.pl"),
    UA("ua.slando", ":flag-ua: OLX.ua"),
    KZ("kz.slando", ":flag-kz: OLX.kz"),
    RO("ro.mercador", ":flag-ro: OLX.ro");

    private String packageName;
    private String name;

    Apps(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getAppName() {
        return this.name;
    }
}

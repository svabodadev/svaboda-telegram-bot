package com.svaboda.telegram.domain;

import lombok.Getter;

import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Getter
public enum Command {

    MAIN("main", "main", PUBLIC, ALL, 0),
    ABOUT_US("aboutus", "about_us", PUBLIC, ALL, 0),
    IN_POLAND("inpoland", "in_poland", PUBLIC, ALL, 0),
    RU_TEST("rutest", "ru_test", PUBLIC, ALL, 0);

    private final String commandName;
    private final String filename;
    private final Privacy privacy;
    private final Locality locality;
    private final int argsNumber;

    Command(String commandName, String filename, Privacy privacy, Locality locality, int argsNumber) {
        this.commandName = commandName;
        this.filename = filename;
        this.privacy = privacy;
        this.locality = locality;
        this.argsNumber = argsNumber;
    }

//    enum Letter {
//        A, B, C, D
//    }
//
//    public static void main(String[] args) {
//        Arrays.asList(Letter.values())
//                .forEach(day -> System.out.println(day));
//
//    }

}

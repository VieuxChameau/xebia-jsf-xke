package fr.xebia.xke.jsfdemo.domain;


import java.util.Date;
import java.util.List;

public class Slot {
    private Integer id;

    private String title;

    private String description;

    private boolean videoAccepted;

    private Date scheduleDate;

    private String duration;

    private User author;

    private List<User> speakers;

    private SlotType slotType;

    //private String file;

    private List<Comment> comments;

    private List<Rating> rates;
}

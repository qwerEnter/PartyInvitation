package com.example.partyinvitation.Model;

import java.sql.Time;
import java.util.Date;

public class ReservationModel {

    private String reservationId;

    private String invite_name, host_name, guest_name, party_agenda, invite_date, invite_time;



    public ReservationModel() {

    }
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getInvite_name() {
        return invite_name;
    }

    public void setInvite_name(String invite_name) {
        this.invite_name = invite_name;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getInvite_date() {
        return invite_date;
    }

    public void setInvite_date(String invite_date) {
        this.invite_date = invite_date;
    }


    public String getInvite_time() {
        return invite_time;
    }

    public void setInvite_time(String invite_time) {
        this.invite_time = invite_time;
    }

    public String getParty_agenda() {
        return party_agenda;
    }

    public void setParty_agenda(String party_agenda) {

        this.party_agenda = party_agenda;
    }
}

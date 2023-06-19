package com.example.partyinvitation.Model;


public class ReservationModel {

    private int reservationId;
    private String invite_name, host_name, guest_name;

    public ReservationModel() {
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
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
}
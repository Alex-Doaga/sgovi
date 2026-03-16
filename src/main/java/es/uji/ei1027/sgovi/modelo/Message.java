package es.uji.ei1027.sgovi.modelo;

import java.time.LocalDate;

public class Message {

    private int idMessage;
    private int negotiationId;
    private int senderId;
    private String messageText;
    private LocalDate dateMsg;

    public Message() {
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getNegotiationId() {
        return negotiationId;
    }

    public void setNegotiationId(int negotiationId) {
        this.negotiationId = negotiationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDate getDateMsg() {
        return dateMsg;
    }

    public void setDateMsg(LocalDate dateMsg) {
        this.dateMsg = dateMsg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", negotiationId=" + negotiationId +
                ", senderId=" + senderId +
                ", messageText='" + messageText + '\'' +
                ", dateMsg=" + dateMsg +
                '}';
    }
}
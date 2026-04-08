package es.uji.ei1027.sgovi.modelo;

import java.time.LocalDateTime;

public class Message {

    public static final String SENDER_OVI_USER = "ovi_user";
    public static final String SENDER_PA = "pa";

    private Integer idMessage;
    private Integer idNegotiation;
    private String senderType;
    private String messageText;
    private LocalDateTime dateMsg;

    public Message() {
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public Integer getIdNegotiation() {
        return idNegotiation;
    }

    public void setIdNegotiation(Integer idNegotiation) {
        this.idNegotiation = idNegotiation;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getDateMsg() {
        return dateMsg;
    }

    public void setDateMsg(LocalDateTime dateMsg) {
        this.dateMsg = dateMsg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", idNegotiation=" + idNegotiation +
                ", senderType='" + senderType + '\'' +
                ", messageText='" + messageText + '\'' +
                ", dateMsg=" + dateMsg +
                '}';
    }
}
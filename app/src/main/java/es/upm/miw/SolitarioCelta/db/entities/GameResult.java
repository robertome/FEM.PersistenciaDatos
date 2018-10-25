package es.upm.miw.SolitarioCelta.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;


@Entity
public class GameResult {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @NonNull
    public String playerName;
    @NonNull
    public Date date = new Date();
    @NonNull
    public Integer tokensNumber;

    public GameResult(@NonNull String playerName, @NonNull Integer tokensNumber) {
        this.playerName = playerName;
        this.tokensNumber = tokensNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTokensNumber() {
        return tokensNumber;
    }

    public void setTokensNumber(Integer tokensNumber) {
        this.tokensNumber = tokensNumber;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", date=" + date +
                ", tokensNumber=" + tokensNumber +
                '}';
    }
}
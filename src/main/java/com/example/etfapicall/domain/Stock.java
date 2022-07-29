package com.example.etfapicall.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stock {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDate date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double adjClose;
    private Double volume;

    public Stock(LocalDate date, Double open, Double high, Double low, Double close, Double adjClose, Double volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }
}

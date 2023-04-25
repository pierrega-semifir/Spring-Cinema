package piga.spring.cinema;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
class Payload {
    private LocalDate date;
    private LocalDateTime dateTime;
}

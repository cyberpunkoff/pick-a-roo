package edu.java.bot.clients.scrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String surname;
}

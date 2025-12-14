package com.example.shoepalace.responseDTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseDTO {
    private String accessToken;
    private String refreshToken;
}

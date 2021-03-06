package dev.highright96.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@ToString
public class MemberDTO {
    private String username;
    private int age;
}

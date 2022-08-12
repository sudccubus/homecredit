package com.homecredit.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private List<String> roleTitleList;
}
